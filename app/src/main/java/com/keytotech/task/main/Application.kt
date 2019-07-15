package com.keytotech.task.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.keytotech.task.koin.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Application: android.app.Application() {

    companion object {
        private const val TAG = "Application"
    }

    override fun onCreate(){
        super.onCreate()

        startKoin {
            androidContext(this@Application)

            modules(Modules.module)
        }
    }
}