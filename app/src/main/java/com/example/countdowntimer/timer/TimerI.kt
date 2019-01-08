package com.example.countdowntimer.timer

import java.lang.Exception

interface TimerI {

    fun start(startTimeMillis: Long, maxTime: Long)
    fun stop(): Long
    fun increaseTimer(milliseconds: Long)

    interface TimerCallback{
        fun onTimeUpdate(millisRemaining: Long)
        fun onDone()
        fun onError(ex: Exception)
    }

}