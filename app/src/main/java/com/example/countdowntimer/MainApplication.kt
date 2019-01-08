package com.example.countdowntimer

import android.app.Application

class MainApplication: Application() {



    override fun onCreate() {
        super.onCreate()
    }


    companion object {
        val MAX_TIME: Long = 2 * 60 *1000L //2 mins in milliseconds
    }
}