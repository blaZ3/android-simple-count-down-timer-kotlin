package com.example.countdowntimer.countDown

import com.example.countdowntimer.timer.TimerI
import java.lang.Exception
import kotlin.concurrent.timer

class CountDownPresenter(private val view: CountDownContract.View, private val timer: TimerI): CountDownContract.Presenter {


    override fun startCountDown(startTimeMillis: Long, maxTimeMillis: Long) {
        timer.start(startTimeMillis, maxTimeMillis, object : TimerI.TimerCallback{
            override fun onTimeUpdate(millisRemaining: Long) {
                view.updateTimer(timeInMillisToString(millisRemaining))
            }

            override fun onDone() {
                view.timerDone()
            }

            override fun onError(ex: Exception) {
                view.showToast(ex.message.toString())
            }
        })
    }

    override fun stopCountDown() {
        timer.stop()
    }

    override fun incrementTimer() {
        timer.increaseTimer(10 * 1000L)
    }

    private fun timeInMillisToString(millis: Long): String{
        val minutes = (millis/1000)/60
        val seconds = (millis/1000) % 60

        return "$minutes : $seconds : ${(millis%1000)/100}"
    }
}