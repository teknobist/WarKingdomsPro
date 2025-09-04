package com.warkingdoms.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.warkingdoms.R
import com.warkingdoms.databinding.ActivitySettingsBinding
import com.warkingdoms.turkuaz.core.utils.PreferenceUtil
import com.warkingdoms.turkuaz.core.utils.MediaService
import com.warkingdoms.turkuaz.core.utils.LanguageUtil

/**
 * Settings Activity - Game configuration and preferences
 * Handles audio, graphics, language, and gameplay settings
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        
        // Setup UI components
        setupUI()
        
        // Load current settings
        loadCurrentSettings()
    }

    private fun setupUI() {
        binding.apply {
            // Back button
            btnBack.setOnClickListener { finish() }
            
            // Audio Settings
            switchMusic.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("music_enabled", isChecked)
                if (isChecked) {
                    MediaService.enableMusic()
                } else {
                    MediaService.disableMusic()
                }
            }
            
            switchSoundEffects.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("sound_effects_enabled", isChecked)
                MediaService.setSoundEffectsEnabled(isChecked)
            }
            
            seekBarMusicVolume.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        val volume = progress / 100f
                        PreferenceUtil.putFloat("music_volume", volume)
                        MediaService.setMusicVolume(volume)
                    }
                }
                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
            })
            
            seekBarSfxVolume.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        val volume = progress / 100f
                        PreferenceUtil.putFloat("sfx_volume", volume)
                        MediaService.setSfxVolume(volume)
                    }
                }
                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
            })
            
            // Graphics Settings
            switchHighQuality.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("high_quality_graphics", isChecked)
                // Apply graphics quality change
            }
            
            switchAnimations.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("animations_enabled", isChecked)
            }
            
            // Language Settings
            spinnerLanguage.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val languages = arrayOf("en", "tr", "de", "fr", "es", "ru", "zh")
                    val selectedLanguage = languages[position]
                    PreferenceUtil.putString("selected_language", selectedLanguage)
                    LanguageUtil.setLanguage(this@SettingsActivity, selectedLanguage)
                }
                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
            }
            
            // Gameplay Settings
            switchNotifications.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("notifications_enabled", isChecked)
            }
            
            switchAutoSave.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("auto_save_enabled", isChecked)
            }
            
            switchVibration.setOnCheckedChangeListener { _, isChecked ->
                PreferenceUtil.putBoolean("vibration_enabled", isChecked)
            }
            
            // Account Settings
            btnResetProgress.setOnClickListener { showResetConfirmation() }
            btnLogout.setOnClickListener { showLogoutConfirmation() }
            btnDeleteAccount.setOnClickListener { showDeleteAccountConfirmation() }
        }
    }

    private fun loadCurrentSettings() {
        binding.apply {
            // Load audio settings
            switchMusic.isChecked = PreferenceUtil.getBoolean("music_enabled", true)
            switchSoundEffects.isChecked = PreferenceUtil.getBoolean("sound_effects_enabled", true)
            seekBarMusicVolume.progress = (PreferenceUtil.getFloat("music_volume", 0.8f) * 100).toInt()
            seekBarSfxVolume.progress = (PreferenceUtil.getFloat("sfx_volume", 0.8f) * 100).toInt()
            
            // Load graphics settings
            switchHighQuality.isChecked = PreferenceUtil.getBoolean("high_quality_graphics", true)
            switchAnimations.isChecked = PreferenceUtil.getBoolean("animations_enabled", true)
            
            // Load language setting
            val currentLanguage = PreferenceUtil.getString("selected_language", "en")
            val languages = arrayOf("en", "tr", "de", "fr", "es", "ru", "zh")
            val languageIndex = languages.indexOf(currentLanguage)
            if (languageIndex >= 0) {
                spinnerLanguage.setSelection(languageIndex)
            }
            
            // Load gameplay settings
            switchNotifications.isChecked = PreferenceUtil.getBoolean("notifications_enabled", true)
            switchAutoSave.isChecked = PreferenceUtil.getBoolean("auto_save_enabled", true)
            switchVibration.isChecked = PreferenceUtil.getBoolean("vibration_enabled", true)
        }
    }

    private fun showResetConfirmation() {
        // Show confirmation dialog for resetting game progress
    }

    private fun showLogoutConfirmation() {
        // Show confirmation dialog for logout
    }

    private fun showDeleteAccountConfirmation() {
        // Show confirmation dialog for account deletion
    }
}