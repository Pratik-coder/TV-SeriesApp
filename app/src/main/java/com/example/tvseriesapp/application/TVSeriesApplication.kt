package com.example.tvseriesapp.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.example.tvseriesapp.di.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TVSeriesApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@TVSeriesApplication)
            modules(mainModule, onTheairModule, airTodayModule, searchTVModule, movieDetailModule, seasonDetailModule,
                favouriteModule, castDetailModule)
        }
        setUpTimberLog()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       MultiDex.install(this)
    }

    private fun setUpTimberLog(){
        Timber.plant(object :Timber.DebugTree(){
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "%s::%s:%s",
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber
                )
            }
        })
    }
}