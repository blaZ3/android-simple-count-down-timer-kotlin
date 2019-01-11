package com.example.countdowntimer.app.countDownScreen

interface CountDownContract {

    interface View {
        fun initView()
        fun updateTimer(timer: String)
        fun timerDone()
        fun showToast(msg: String)
    }

    interface Presenter {
        fun startCountDown(startTimeMillis: Long, maxTimeMillis: Long)
        fun stopCountDown(): Long
        fun incrementTimer()
    }

}