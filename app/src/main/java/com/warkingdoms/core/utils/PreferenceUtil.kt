package com.warkingdoms.turkuaz.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Utility class for managing encrypted shared preferences
 * Provides secure storage for user settings and game data
 */
object PreferenceUtil {

    private const val PREFS_NAME = "war_kingdoms_prefs"
    private const val SECURE_PREFS_NAME = "war_kingdoms_secure_prefs"
    
    private lateinit var preferences: SharedPreferences
    private lateinit var securePreferences: SharedPreferences
    private var isInitialized = false

    /**
     * Initialize preferences with application context
     */
    fun initialize(context: Context) {
        if (!isInitialized) {
            // Regular preferences for non-sensitive data
            preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            
            // Encrypted preferences for sensitive data
            try {
                val masterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
                
                securePreferences = EncryptedSharedPreferences.create(
                    context,
                    SECURE_PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: Exception) {
                // Fallback to regular preferences if encryption fails
                securePreferences = context.getSharedPreferences(SECURE_PREFS_NAME, Context.MODE_PRIVATE)
            }
            
            isInitialized = true
        }
    }

    // String operations
    fun putString(key: String, value: String, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = "", secure: Boolean = false): String {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    // Boolean operations
    fun putBoolean(key: String, value: Boolean, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false, secure: Boolean = false): Boolean {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getBoolean(key, defaultValue)
    }

    // Integer operations
    fun putInt(key: String, value: Int, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0, secure: Boolean = false): Int {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getInt(key, defaultValue)
    }

    // Long operations
    fun putLong(key: String, value: Long, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long = 0L, secure: Boolean = false): Long {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getLong(key, defaultValue)
    }

    // Float operations
    fun putFloat(key: String, value: Float, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defaultValue: Float = 0f, secure: Boolean = false): Float {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getFloat(key, defaultValue)
    }

    // Set operations
    fun putStringSet(key: String, value: Set<String>, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String, defaultValue: Set<String> = emptySet(), secure: Boolean = false): Set<String> {
        val prefs = if (secure) securePreferences else preferences
        return prefs.getStringSet(key, defaultValue) ?: defaultValue
    }

    // Utility operations
    fun contains(key: String, secure: Boolean = false): Boolean {
        val prefs = if (secure) securePreferences else preferences
        return prefs.contains(key)
    }

    fun remove(key: String, secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().remove(key).apply()
    }

    fun clear(secure: Boolean = false) {
        val prefs = if (secure) securePreferences else preferences
        prefs.edit().clear().apply()
    }

    fun clearAll() {
        preferences.edit().clear().apply()
        securePreferences.edit().clear().apply()
    }

    // Game-specific convenience methods
    fun saveUserToken(token: String) {
        putString("user_token", token, secure = true)
    }

    fun getUserToken(): String {
        return getString("user_token", secure = true)
    }

    fun saveUserId(userId: String) {
        putString("user_id", userId, secure = true)
    }

    fun getUserId(): String {
        return getString("user_id", secure = true)
    }

    fun isUserLoggedIn(): Boolean {
        return getBoolean("user_logged_in", secure = true)
    }

    fun setUserLoggedIn(loggedIn: Boolean) {
        putBoolean("user_logged_in", loggedIn, secure = true)
    }
}