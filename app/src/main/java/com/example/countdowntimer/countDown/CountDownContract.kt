package com.example.countdowntimer.countDown

interface CountDownContract {

    interface View{
        fun initView()
        fun updateTimer(timer: String)
        fun timerDone()
        fun showToast(msg: String)
    }

    interface Presenter{
        fun startCountDown(startTimeMillis: Long, maxTimeMillis: Long)
        fun stopCountDown()
        fun incrementTimer()
    }

}