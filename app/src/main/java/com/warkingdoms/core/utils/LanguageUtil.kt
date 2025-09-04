package com.warkingdoms.core.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

/**
 * Language Utility for managing app localization
 * Handles language switching and locale management
 */
object LanguageUtil {

    private const val LANGUAGE_KEY = "selected_language"
    private var currentLanguage: String = "en"
    
    // Supported languages
    private val supportedLanguages = mapOf(
        "en" to "English",
        "tr" to "Türkçe",
        "de" to "Deutsch",
        "fr" to "Français",
        "es" to "Español",
        "ru" to "Русский",
        "zh" to "中文",
        "ar" to "العربية",
        "pt" to "Português",
        "it" to "Italiano",
        "ja" to "日本語",
        "ko" to "한국어"
    )

    /**
     * Initialize language utility
     */
    fun initialize(context: Context) {
        currentLanguage = PreferenceUtil.getString(LANGUAGE_KEY, getSystemLanguage())
        setLanguage(context, currentLanguage)
    }

    /**
     * Get system default language
     */
    private fun getSystemLanguage(): String {
        val systemLanguage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0].language
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale.language
        }
        
        return if (supportedLanguages.containsKey(systemLanguage)) {
            systemLanguage
        } else {
            "en" // Default to English if system language is not supported
        }
    }

    /**
     * Set application language
     */
    fun setLanguage(context: Context, languageCode: String) {
        if (!supportedLanguages.containsKey(languageCode)) {
            return
        }
        
        currentLanguage = languageCode
        PreferenceUtil.putString(LANGUAGE_KEY, languageCode)
        
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }

    /**
     * Get current language code
     */
    fun getCurrentLanguage(): String {
        return currentLanguage
    }

    /**
     * Get current language display name
     */
    fun getCurrentLanguageName(): String {
        return supportedLanguages[currentLanguage] ?: "English"
    }

    /**
     * Get all supported languages
     */
    fun getSupportedLanguages(): Map<String, String> {
        return supportedLanguages.toMap()
    }

    /**
     * Check if language is supported
     */
    fun isLanguageSupported(languageCode: String): Boolean {
        return supportedLanguages.containsKey(languageCode)
    }

    /**
     * Get language display name by code
     */
    fun getLanguageName(languageCode: String): String {
        return supportedLanguages[languageCode] ?: "Unknown"
    }

    /**
     * Check if current language is RTL (Right-to-Left)
     */
    fun isRTL(): Boolean {
        return when (currentLanguage) {
            "ar", "he", "fa", "ur" -> true
            else -> false
        }
    }

    /**
     * Get localized string resource
     */
    fun getString(context: Context, resourceId: Int): String {
        return try {
            context.getString(resourceId)
        } catch (e: Exception) {
            "String not found"
        }
    }

    /**
     * Get localized string resource with formatting
     */
    fun getString(context: Context, resourceId: Int, vararg formatArgs: Any): String {
        return try {
            context.getString(resourceId, *formatArgs)
        } catch (e: Exception) {
            "String not found"
        }
    }

    /**
     * Format number according to current locale
     */
    fun formatNumber(number: Long): String {
        return try {
            val locale = Locale(currentLanguage)
            java.text.NumberFormat.getNumberInstance(locale).format(number)
        } catch (e: Exception) {
            number.toString()
        }
    }

    /**
     * Format currency according to current locale
     */
    fun formatCurrency(amount: Double, currencyCode: String = "USD"): String {
        return try {
            val locale = Locale(currentLanguage)
            val currency = Currency.getInstance(currencyCode)
            val formatter = java.text.NumberFormat.getCurrencyInstance(locale)
            formatter.currency = currency
            formatter.format(amount)
        } catch (e: Exception) {
            "$amount $currencyCode"
        }
    }

    /**
     * Format date according to current locale
     */
    fun formatDate(timestamp: Long, pattern: String = "dd/MM/yyyy"): String {
        return try {
            val locale = Locale(currentLanguage)
            val formatter = java.text.SimpleDateFormat(pattern, locale)
            formatter.format(Date(timestamp))
        } catch (e: Exception) {
            Date(timestamp).toString()
        }
    }

    /**
     * Format time according to current locale
     */
    fun formatTime(timestamp: Long, pattern: String = "HH:mm"): String {
        return try {
            val locale = Locale(currentLanguage)
            val formatter = java.text.SimpleDateFormat(pattern, locale)
            formatter.format(Date(timestamp))
        } catch (e: Exception) {
            Date(timestamp).toString()
        }
    }

    /**
     * Get language-specific font family
     */
    fun getFontFamily(): String {
        return when (currentLanguage) {
            "zh", "ja", "ko" -> "sans-serif-medium" // Better for Asian languages
            "ar", "fa", "ur" -> "sans-serif" // RTL languages
            else -> "sans-serif" // Default for Latin-based languages
        }
    }

    /**
     * Get appropriate text size multiplier for current language
     */
    fun getTextSizeMultiplier(): Float {
        return when (currentLanguage) {
            "zh", "ja", "ko" -> 1.1f // Slightly larger for better readability
            "ar", "th", "hi" -> 1.05f // Slightly larger for complex scripts
            else -> 1.0f // Default size
        }
    }
}