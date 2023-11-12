package com.homelandpay.codigodietplan

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: This is inside My application's on create")
    }
    companion object{
        const val TAG = "MyApplication"
    }
}