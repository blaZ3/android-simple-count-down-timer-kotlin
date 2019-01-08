package com.example.countdowntimer.timer

interface TimerI {

    fun start(startTimeMillis: Long, maxTime: Long, callback: TimerCallback)
    fun stop(): Long
    fun increaseTimer(milliseconds: Long)

    interface TimerCallback{
        fun onTimeUpdate(millisRemaining: Long)
        fun onDone()
        fun onError(ex: Exception)
    }

}


sealed class TimerException(message: String): Exception(message)
object TimerMaxLimitReachedException: TimerException("Cannot increase time more than max time")
object TimerNotStartedException: TimerException("Timer is not running")