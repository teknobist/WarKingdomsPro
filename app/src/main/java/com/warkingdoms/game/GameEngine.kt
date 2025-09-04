package com.warkingdoms.game

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.warkingdoms.core.utils.PreferenceUtil
import com.warkingdoms.core.utils.MediaService
import com.warkingdoms.game.data.GameState
import com.warkingdoms.game.data.Player
import com.warkingdoms.game.data.Kingdom
import com.warkingdoms.game.managers.ResourceManager
import com.warkingdoms.game.managers.BuildingManager
import com.warkingdoms.game.managers.ArmyManager
import com.warkingdoms.game.managers.BattleManager
import com.warkingdoms.game.managers.NetworkManager
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Core Game Engine for War Kingdoms Strategy Game
 * Manages game state, updates, and coordination between managers
 */
class GameEngine(private val context: Context) {

    companion object {
        private const val GAME_UPDATE_INTERVAL = 1000L // 1 second
        private const val SAVE_INTERVAL = 30000L // 30 seconds
        private const val NETWORK_SYNC_INTERVAL = 60000L // 1 minute
    }

    // Core components
    private val gameScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val handler = Handler(Looper.getMainLooper())
    private val isRunning = AtomicBoolean(false)
    private val isPaused = AtomicBoolean(false)
    
    // Game state
    private lateinit var gameState: GameState
    private lateinit var currentPlayer: Player
    private lateinit var playerKingdom: Kingdom
    
    // Managers
    private lateinit var resourceManager: ResourceManager
    private lateinit var buildingManager: BuildingManager
    private lateinit var armyManager: ArmyManager
    private lateinit var battleManager: BattleManager
    private lateinit var networkManager: NetworkManager
    
    // Update runnables
    private val gameUpdateRunnable = object : Runnable {
        override fun run() {
            if (isRunning.get() && !isPaused.get()) {
                updateGame()
                handler.postDelayed(this, GAME_UPDATE_INTERVAL)
            }
        }
    }
    
    private val saveGameRunnable = object : Runnable {
        override fun run() {
            if (isRunning.get()) {
                saveGameState()
                handler.postDelayed(this, SAVE_INTERVAL)
            }
        }
    }
    
    private val networkSyncRunnable = object : Runnable {
        override fun run() {
            if (isRunning.get() && !isPaused.get()) {
                syncWithServer()
                handler.postDelayed(this, NETWORK_SYNC_INTERVAL)
            }
        }
    }

    /**
     * Initialize the game engine
     */
    fun initialize() {
        try {
            // Initialize managers
            initializeManagers()
            
            // Load or create game state
            loadGameState()
            
            // Initialize audio
            MediaService.initialize(context)
            
            // Start background music
            MediaService.playGameMusic("game_background_music")
            
            isRunning.set(true)
            
            // Start update loops
            startUpdateLoops()
            
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle initialization error
        }
    }

    private fun initializeManagers() {
        resourceManager = ResourceManager()
        buildingManager = BuildingManager()
        armyManager = ArmyManager()
        battleManager = BattleManager()
        networkManager = NetworkManager(context)
    }

    private fun loadGameState() {
        // Try to load existing game state
        val savedGameData = PreferenceUtil.getString("game_state", "")
        
        if (savedGameData.isNotEmpty()) {
            try {
                // Parse saved game state
                gameState = GameState.fromJson(savedGameData)
                currentPlayer = gameState.currentPlayer
                playerKingdom = currentPlayer.kingdom
            } catch (e: Exception) {
                // If loading fails, create new game
                createNewGame()
            }
        } else {
            // Create new game
            createNewGame()
        }
        
        // Initialize managers with game state
        resourceManager.initialize(playerKingdom)
        buildingManager.initialize(playerKingdom)
        armyManager.initialize(playerKingdom)
    }

    private fun createNewGame() {
        // Create new player and kingdom
        currentPlayer = Player(
            id = generatePlayerId(),
            name = PreferenceUtil.getString("player_name", "Player"),
            level = 1,
            experience = 0
        )
        
        playerKingdom = Kingdom(
            playerId = currentPlayer.id,
            name = PreferenceUtil.getString("kingdom_name", "My Kingdom"),
            level = 1
        )
        
        currentPlayer.kingdom = playerKingdom
        
        gameState = GameState(
            currentPlayer = currentPlayer,
            gameVersion = "1.0.0",
            lastSaveTime = System.currentTimeMillis()
        )
        
        // Give starting resources
        giveStartingResources()
    }

    private fun generatePlayerId(): String {
        return "player_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }

    private fun giveStartingResources() {
        playerKingdom.resources.apply {
            gold = 1000
            food = 500
            wood = 300
            stone = 200
            iron = 100
        }
    }

    private fun startUpdateLoops() {
        handler.post(gameUpdateRunnable)
        handler.post(saveGameRunnable)
        handler.post(networkSyncRunnable)
    }

    /**
     * Main game update loop
     */
    private fun updateGame() {
        try {
            val currentTime = System.currentTimeMillis()
            val deltaTime = currentTime - gameState.lastUpdateTime
            gameState.lastUpdateTime = currentTime
            
            // Update managers
            resourceManager.update(deltaTime)
            buildingManager.update(deltaTime)
            armyManager.update(deltaTime)
            battleManager.update(deltaTime)
            
            // Update game state
            updateGameState(deltaTime)
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateGameState(deltaTime: Long) {
        // Update player experience and level
        // Update kingdom statistics
        // Process timed events
        // Check for achievements
    }

    /**
     * Save game state to local storage
     */
    private fun saveGameState() {
        try {
            gameState.lastSaveTime = System.currentTimeMillis()
            val gameStateJson = gameState.toJson()
            PreferenceUtil.putString("game_state", gameStateJson)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Sync game state with server
     */
    private fun syncWithServer() {
        gameScope.launch {
            try {
                networkManager.syncGameState(gameState)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle network sync error
            }
        }
    }

    /**
     * Pause the game engine
     */
    fun pause() {
        isPaused.set(true)
        MediaService.pauseGameMusic()
    }

    /**
     * Resume the game engine
     */
    fun resume() {
        isPaused.set(false)
        MediaService.resumeGameMusic()
        
        if (isRunning.get()) {
            handler.post(gameUpdateRunnable)
        }
    }

    /**
     * Stop and cleanup the game engine
     */
    fun cleanup() {
        isRunning.set(false)
        isPaused.set(true)
        
        // Remove all callbacks
        handler.removeCallbacks(gameUpdateRunnable)
        handler.removeCallbacks(saveGameRunnable)
        handler.removeCallbacks(networkSyncRunnable)
        
        // Save final game state
        saveGameState()
        
        // Cleanup managers
        resourceManager.cleanup()
        buildingManager.cleanup()
        armyManager.cleanup()
        battleManager.cleanup()
        networkManager.cleanup()
        
        // Cancel coroutines
        gameScope.cancel()
        
        // Stop music
        MediaService.stopGameMusic()
    }

    // Getters for game components
    fun getGameState(): GameState = gameState
    fun getCurrentPlayer(): Player = currentPlayer
    fun getPlayerKingdom(): Kingdom = playerKingdom
    fun getResourceManager(): ResourceManager = resourceManager
    fun getBuildingManager(): BuildingManager = buildingManager
    fun getArmyManager(): ArmyManager = armyManager
    fun getBattleManager(): BattleManager = battleManager
    fun getNetworkManager(): NetworkManager = networkManager
    
    // Game state queries
    fun isGameRunning(): Boolean = isRunning.get()
    fun isGamePaused(): Boolean = isPaused.get()
}