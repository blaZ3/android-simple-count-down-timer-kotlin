package com.example.countdowntimer.countDown

import com.example.countdowntimer.timer.TimerException
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
        try {
            timer.increaseTimer(10 * 1000L)
        } catch (ex: TimerException){
            view.showToast(ex.message.toString())
        }
    }

    private fun timeInMillisToString(millis: Long): String{
        val minutes = (millis/1000)/60
        val seconds = (millis/1000) % 60

        return if (minutes>0){
            "$minutes : $seconds : ${(millis%1000)/100}"
        }else{
            "$seconds : ${(millis%1000)/100}"
        }
    }
}