package com.example.countdowntimer.app.timer

interface TimerI {

    fun start(startTimeMillis: Long, maxTime: Long, callback: TimerCallback)
    fun stop(): Long
    fun increaseTimer(milliseconds: Long, callback: ChangeTimerCallback)

    interface TimerCallback {
        fun onTimeUpdate(millisRemaining: Long)
        fun onDone()
        fun onError(ex: Exception)
    }

    interface ChangeTimerCallback {
        fun onTimeChanged(millisRemaining: Long)
        fun onError(ex: Exception)
    }

}

sealed class TimerException(message: String) : Exception(message)
object TimerMaxLimitReachedException : TimerException("Cannot increase time more than max time")
object TimerAlreadyStartedException : TimerException("Timer is already running")
object TimerNotStartedException : TimerException("Timer is not running")