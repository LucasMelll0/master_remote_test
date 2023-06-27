package com.example.master_remote_test

import android.app.Application
import com.example.master_remote_test.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MasterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MasterApplication)
            modules(appModule)
        }
    }
}