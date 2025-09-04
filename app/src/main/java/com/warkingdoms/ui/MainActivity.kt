package com.warkingdoms.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.warkingdoms.R
import com.warkingdoms.databinding.ActivityMainBinding
import com.warkingdoms.turkuaz.core.utils.ScreenUtil
import com.warkingdoms.turkuaz.core.utils.PreferenceUtil
import com.warkingdoms.turkuaz.core.utils.MediaService

/**
 * Main Activity - Entry point of the application
 * Handles splash screen, user authentication, and navigation to game
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isGameReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set full screen and landscape orientation
        setupFullScreen()
        
        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        
        // Initialize UI components
        initializeUI()
        
        // Check if user is logged in
        checkUserSession()
    }

    private fun setupFullScreen() {
        // Hide status bar and navigation bar
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        
        // Keep screen on during gameplay
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Set landscape orientation
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun initializeUI() {
        // Initialize splash screen elements
        binding.apply {
            // Set up click listeners
            btnPlay.setOnClickListener { startGame() }
            btnSettings.setOnClickListener { openSettings() }
            btnExit.setOnClickListener { exitGame() }
        }
    }

    private fun checkUserSession() {
        val isLoggedIn = PreferenceUtil.getBoolean("user_logged_in", false)
        if (isLoggedIn) {
            // User is logged in, prepare game
            prepareGame()
        } else {
            // Show login/registration options
            showAuthenticationOptions()
        }
    }

    private fun prepareGame() {
        // Initialize game components
        // Load user data
        // Prepare game assets
        isGameReady = true
        binding.btnPlay.isEnabled = true
    }

    private fun showAuthenticationOptions() {
        // Show login/register dialog or navigate to auth activity
    }

    private fun startGame() {
        if (isGameReady) {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun exitGame() {
        finish()
    }

    override fun onResume() {
        super.onResume()
        // Resume background music if enabled
        MediaService.resumeBackgroundMusic()
    }

    override fun onPause() {
        super.onPause()
        // Pause background music
        MediaService.pauseBackgroundMusic()
    }
}