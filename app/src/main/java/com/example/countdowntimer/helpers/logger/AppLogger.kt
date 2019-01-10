package com.example.countdowntimer.helpers.logger

import android.util.Log

class AppLogger(private val isDebug: Boolean): LoggerI {

    override fun d(tag: String, msg: String) {
        if (isDebug){
            Log.d(tag, msg)
        }
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}