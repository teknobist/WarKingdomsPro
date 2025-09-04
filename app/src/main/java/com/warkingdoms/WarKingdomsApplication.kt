package com.warkingdoms

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.warkingdoms.core.di.DaggerAppComponent
import com.warkingdoms.core.di.AppComponent
import com.warkingdoms.core.utils.PreferenceUtil
import com.warkingdoms.core.utils.LanguageUtil

/**
 * Main Application class for War Kingdoms Strategy Game
 * Handles dependency injection, multi-dex support, and global initialization
 */
class WarKingdomsApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var instance: WarKingdomsApplication
            private set

        @JvmStatic
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize dependency injection
        initializeDagger()
        
        // Initialize preferences
        PreferenceUtil.initialize(this)
        
        // Initialize language settings
        LanguageUtil.initialize(this)
        
        // Initialize other core components
        initializeCoreComponents()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    private fun initializeCoreComponents() {
        // Initialize media service
        // Initialize network components
        // Initialize game engine components
    }
}