package com.warkingdoms.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.warkingdoms.R
import com.warkingdoms.databinding.ActivityGameBinding
import com.warkingdoms.game.GameEngine
import com.warkingdoms.game.fragments.MapFragment
import com.warkingdoms.game.fragments.BuildingFragment
import com.warkingdoms.game.fragments.ArmyFragment
import com.warkingdoms.game.fragments.ChatFragment
import com.warkingdoms.core.utils.MediaService
import com.warkingdoms.core.utils.PreferenceUtil

/**
 * Main Game Activity - Core gameplay interface
 * Manages game fragments, UI navigation, and game state
 */
class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameEngine: GameEngine
    
    // Fragment management
    private var currentFragment: Fragment? = null
    private val mapFragment by lazy { MapFragment() }
    private val buildingFragment by lazy { BuildingFragment() }
    private val armyFragment by lazy { ArmyFragment() }
    private val chatFragment by lazy { ChatFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set full screen mode
        setupFullScreen()
        
        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        
        // Initialize game engine
        initializeGameEngine()
        
        // Setup UI components
        setupUI()
        
        // Load initial fragment (Map)
        loadFragment(mapFragment)
    }

    private fun setupFullScreen() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun initializeGameEngine() {
        gameEngine = GameEngine(this)
        gameEngine.initialize()
    }

    private fun setupUI() {
        binding.apply {
            // Bottom navigation setup
            btnMap.setOnClickListener { loadFragment(mapFragment) }
            btnBuilding.setOnClickListener { loadFragment(buildingFragment) }
            btnArmy.setOnClickListener { loadFragment(armyFragment) }
            btnChat.setOnClickListener { loadFragment(chatFragment) }
            
            // Top bar setup
            btnSettings.setOnClickListener { openGameSettings() }
            btnProfile.setOnClickListener { openProfile() }
            btnAlliance.setOnClickListener { openAlliance() }
            
            // Resource display
            updateResourceDisplay()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (currentFragment != fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            currentFragment = fragment
            updateNavigationButtons(fragment)
        }
    }

    private fun updateNavigationButtons(activeFragment: Fragment) {
        binding.apply {
            // Reset all button states
            btnMap.isSelected = false
            btnBuilding.isSelected = false
            btnArmy.isSelected = false
            btnChat.isSelected = false
            
            // Set active button
            when (activeFragment) {
                mapFragment -> btnMap.isSelected = true
                buildingFragment -> btnBuilding.isSelected = true
                armyFragment -> btnArmy.isSelected = true
                chatFragment -> btnChat.isSelected = true
            }
        }
    }

    private fun updateResourceDisplay() {
        // Update gold, food, wood, stone, iron displays
        // This will be connected to game engine data
    }

    private fun openGameSettings() {
        // Open in-game settings dialog
    }

    private fun openProfile() {
        // Open player profile
    }

    private fun openAlliance() {
        // Open alliance management
    }

    override fun onResume() {
        super.onResume()
        gameEngine.resume()
        MediaService.resumeGameMusic()
    }

    override fun onPause() {
        super.onPause()
        gameEngine.pause()
        MediaService.pauseGameMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameEngine.cleanup()
    }

    override fun onBackPressed() {
        // Handle back button - show exit confirmation
        showExitConfirmation()
    }

    private fun showExitConfirmation() {
        // Show dialog asking if user wants to exit game
    }
}