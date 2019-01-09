package com.example.countdowntimer.timer

import android.os.Handler
import android.os.HandlerThread

class Timer : TimerI {
    private val LOCK = java.lang.Object()
    private val DELAY_MILLIS = 100L

    @Volatile var milliSecondsRemaining: Long = 0L
    private var maxTime: Long = 0L

    private var isStopped: Boolean = false
    private var isRunning: Boolean = false

    private lateinit var timerThread: HandlerThread
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable:Runnable

    override fun start(startTimeMillis: Long, maxTime: Long, callback: TimerI.TimerCallback) {
        if (isRunning){
            callback.onError(TimerAlreadyStartedException)
            return
        }

        this.milliSecondsRemaining = startTimeMillis
        this.maxTime = maxTime

        timerThread = HandlerThread("Timer")
        timerThread.start()
        timerHandler = Handler(timerThread.looper)

        timerRunnable = Runnable {
            synchronized(LOCK){
                milliSecondsRemaining -= DELAY_MILLIS
                if (!isStopped){
                    if (milliSecondsRemaining > 0){
                        callback.onTimeUpdate(milliSecondsRemaining)
                        timerHandler.postDelayed(timerRunnable, DELAY_MILLIS)
                    } else{
                        callback.onDone()
                        stopTimer()
                    }
                } else{
                    stopTimer()
                }
                LOCK.notifyAll()
            }
        }

        timerHandler.post(timerRunnable)
        isRunning = true
    }

    override fun stop(): Long {
        if (isRunning){
            isStopped = true
            return milliSecondsRemaining
        }else {
            throw TimerNotStartedException
        }
    }

    override fun increaseTimer(milliseconds: Long): Long {
        if(isRunning){
            synchronized(LOCK){
                if ((milliSecondsRemaining + milliseconds) > maxTime){
                    LOCK.notifyAll()
                    throw TimerMaxLimitReachedException
                } else{
                    milliSecondsRemaining += milliseconds
                    LOCK.notifyAll()
                    return milliSecondsRemaining
                }
            }
        }else{
            throw TimerNotStartedException
        }
    }

    private fun stopTimer(){
        isRunning = false

        if (timerThread.isAlive){
            timerThread.quitSafely()
        }
    }
}