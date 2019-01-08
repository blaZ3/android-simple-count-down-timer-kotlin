package com.example.countdowntimer.timer

import android.os.Handler

class Timer : TimerI {
    private val LOCK = java.lang.Object()
    private val DELAY_MILLIS = 100L

    @Volatile var milliSecondsRemaining: Long = 0L
    private var maxTime: Long = 0L
    private var callback: TimerI.TimerCallback? = null

    private var isStopped: Boolean = false
    private var isRunning: Boolean = false

    private val delayedHandler:Handler = Handler()
    private lateinit var runnable:Runnable

    override fun start(startTimeMillis: Long, maxTime: Long, callback: TimerI.TimerCallback) {
        this.milliSecondsRemaining = startTimeMillis
        this.maxTime = maxTime
        this.callback = callback

        runnable = Runnable {
            synchronized(LOCK){
                milliSecondsRemaining -= DELAY_MILLIS

                if (milliSecondsRemaining > 0 && !isStopped){
                    callback.onTimeUpdate(milliSecondsRemaining)
                    delayedHandler.postDelayed(runnable, DELAY_MILLIS)
                } else{
                    callback.onDone()
                    isRunning = false
                }

                LOCK.notifyAll()
            }
        }

        delayedHandler.post(runnable)
        isRunning = true
    }

    override fun stop(): Long {
        isStopped = true
        isRunning = false

        return milliSecondsRemaining
    }

    override fun increaseTimer(milliseconds: Long) {
        if(isRunning){
            synchronized(LOCK){
                if ((milliSecondsRemaining + milliseconds) > maxTime){
                    callback?.onError(TimerMaxLimitReachedException)
                } else{
                    milliSecondsRemaining += milliseconds
                }
                LOCK.notifyAll()
            }
        }else{
            callback?.onError(TimerNotStartedException)
        }

    }
}