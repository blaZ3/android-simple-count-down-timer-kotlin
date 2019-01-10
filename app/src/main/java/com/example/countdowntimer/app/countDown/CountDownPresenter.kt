package com.example.countdowntimer.app.countDown

import com.example.countdowntimer.R
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI

class CountDownPresenter(private val view: CountDownContract.View,
                         private val timer: TimerI,
                         private val stringFetcher: StringFetcherI,
                         private val logger: LoggerI):
    CountDownContract.Presenter {


    override fun startCountDown(startTimeMillis: Long, maxTimeMillis: Long) {
        timer.start(startTimeMillis, maxTimeMillis, object : TimerI.TimerCallback{
            override fun onTimeUpdate(millisRemaining: Long) {
                logger.d("onTimeUpdate", "$millisRemaining")
                view.updateTimer(timeInMillisToString(millisRemaining))
            }

            override fun onDone() {
                logger.d("onDone", "Timer Done")
                view.timerDone()
            }

            override fun onError(ex: Exception) {
                logger.e("onError", ex.message.toString())
                view.showToast(ex.message.toString())
            }
        })
    }

    override fun stopCountDown() {
        timer.stop()
    }

    override fun incrementTimer() {
        timer.increaseTimer(10 * 1000L, object : TimerI.ChangeTimerCallback{
            override fun onTimeChanged(millisRemaining: Long) {
                logger.d("onTimeChanged", "$millisRemaining")
                view.showToast(stringFetcher.getString(R.string.str_time_changed))
            }
            override fun onError(ex: Exception) {
                logger.e("onError", ex.message.toString())
                view.showToast(ex.message.toString())
            }
        })
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