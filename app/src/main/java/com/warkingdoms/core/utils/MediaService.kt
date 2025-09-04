package com.warkingdoms.core.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import java.util.concurrent.ConcurrentHashMap

/**
 * Media Service for managing game audio
 * Handles background music, sound effects, and audio settings
 */
object MediaService {

    private var context: Context? = null
    private var backgroundMusicPlayer: MediaPlayer? = null
    private var gameMusicPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private val soundMap = ConcurrentHashMap<String, Int>()
    private val handler = Handler(Looper.getMainLooper())
    
    // Audio settings
    private var isMusicEnabled = true
    private var areSoundEffectsEnabled = true
    private var musicVolume = 0.8f
    private var sfxVolume = 0.8f
    
    // Current playing states
    private var isBackgroundMusicPlaying = false
    private var isGameMusicPlaying = false
    private var currentBackgroundMusic: String? = null
    private var currentGameMusic: String? = null

    /**
     * Initialize the media service
     */
    fun initialize(context: Context) {
        this.context = context
        initializeSoundPool()
        loadSettings()
    }

    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    private fun loadSettings() {
        isMusicEnabled = PreferenceUtil.getBoolean("music_enabled", true)
        areSoundEffectsEnabled = PreferenceUtil.getBoolean("sound_effects_enabled", true)
        musicVolume = PreferenceUtil.getFloat("music_volume", 0.8f)
        sfxVolume = PreferenceUtil.getFloat("sfx_volume", 0.8f)
    }

    // Background Music Methods
    fun playBackgroundMusic(musicName: String, loop: Boolean = true) {
        if (!isMusicEnabled || currentBackgroundMusic == musicName) return
        
        stopBackgroundMusic()
        
        try {
            context?.let { ctx ->
                val resId = ctx.resources.getIdentifier(musicName, "raw", ctx.packageName)
                if (resId != 0) {
                    backgroundMusicPlayer = MediaPlayer.create(ctx, resId)
                    backgroundMusicPlayer?.apply {
                        isLooping = loop
                        setVolume(musicVolume, musicVolume)
                        setOnCompletionListener {
                            if (!loop) {
                                isBackgroundMusicPlaying = false
                                currentBackgroundMusic = null
                            }
                        }
                        start()
                        isBackgroundMusicPlaying = true
                        currentBackgroundMusic = musicName
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseBackgroundMusic() {
        backgroundMusicPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isBackgroundMusicPlaying = false
            }
        }
    }

    fun resumeBackgroundMusic() {
        if (isMusicEnabled) {
            backgroundMusicPlayer?.let {
                if (!it.isPlaying && !isBackgroundMusicPlaying) {
                    it.start()
                    isBackgroundMusicPlaying = true
                }
            }
        }
    }

    fun stopBackgroundMusic() {
        backgroundMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        backgroundMusicPlayer = null
        isBackgroundMusicPlaying = false
        currentBackgroundMusic = null
    }

    // Game Music Methods
    fun playGameMusic(musicName: String, loop: Boolean = true) {
        if (!isMusicEnabled || currentGameMusic == musicName) return
        
        stopGameMusic()
        
        try {
            context?.let { ctx ->
                val resId = ctx.resources.getIdentifier(musicName, "raw", ctx.packageName)
                if (resId != 0) {
                    gameMusicPlayer = MediaPlayer.create(ctx, resId)
                    gameMusicPlayer?.apply {
                        isLooping = loop
                        setVolume(musicVolume, musicVolume)
                        setOnCompletionListener {
                            if (!loop) {
                                isGameMusicPlaying = false
                                currentGameMusic = null
                            }
                        }
                        start()
                        isGameMusicPlaying = true
                        currentGameMusic = musicName
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseGameMusic() {
        gameMusicPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isGameMusicPlaying = false
            }
        }
    }

    fun resumeGameMusic() {
        if (isMusicEnabled) {
            gameMusicPlayer?.let {
                if (!it.isPlaying && !isGameMusicPlaying) {
                    it.start()
                    isGameMusicPlaying = true
                }
            }
        }
    }

    fun stopGameMusic() {
        gameMusicPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        gameMusicPlayer = null
        isGameMusicPlaying = false
        currentGameMusic = null
    }

    // Sound Effects Methods
    fun loadSoundEffect(soundName: String): Boolean {
        return try {
            context?.let { ctx ->
                val resId = ctx.resources.getIdentifier(soundName, "raw", ctx.packageName)
                if (resId != 0) {
                    val soundId = soundPool?.load(ctx, resId, 1)
                    soundId?.let {
                        soundMap[soundName] = it
                        true
                    } ?: false
                } else false
            } ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun playSoundEffect(soundName: String, volume: Float = sfxVolume) {
        if (!areSoundEffectsEnabled) return
        
        val soundId = soundMap[soundName]
        if (soundId != null) {
            soundPool?.play(soundId, volume, volume, 1, 0, 1.0f)
        } else {
            // Try to load and play immediately
            if (loadSoundEffect(soundName)) {
                handler.postDelayed({
                    soundMap[soundName]?.let { id ->
                        soundPool?.play(id, volume, volume, 1, 0, 1.0f)
                    }
                }, 100)
            }
        }
    }

    // Settings Methods
    fun enableMusic() {
        isMusicEnabled = true
        if (currentBackgroundMusic != null) {
            resumeBackgroundMusic()
        }
        if (currentGameMusic != null) {
            resumeGameMusic()
        }
    }

    fun disableMusic() {
        isMusicEnabled = false
        pauseBackgroundMusic()
        pauseGameMusic()
    }

    fun setSoundEffectsEnabled(enabled: Boolean) {
        areSoundEffectsEnabled = enabled
    }

    fun setMusicVolume(volume: Float) {
        musicVolume = volume.coerceIn(0f, 1f)
        backgroundMusicPlayer?.setVolume(musicVolume, musicVolume)
        gameMusicPlayer?.setVolume(musicVolume, musicVolume)
    }

    fun setSfxVolume(volume: Float) {
        sfxVolume = volume.coerceIn(0f, 1f)
    }

    // Cleanup
    fun cleanup() {
        stopBackgroundMusic()
        stopGameMusic()
        soundPool?.release()
        soundPool = null
        soundMap.clear()
        context = null
    }

    // Convenience methods for common game sounds
    fun playButtonClick() = playSoundEffect("button_click")
    fun playBuildingComplete() = playSoundEffect("building_complete")
    fun playBattleStart() = playSoundEffect("battle_start")
    fun playVictory() = playSoundEffect("victory")
    fun playDefeat() = playSoundEffect("defeat")
    fun playResourceCollect() = playSoundEffect("resource_collect")
    fun playNotification() = playSoundEffect("notification")
}