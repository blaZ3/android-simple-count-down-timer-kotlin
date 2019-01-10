package com.example.countdowntimer.app.countDownScreen

import com.example.countdowntimer.R
import com.example.countdowntimer.app.timer.TimerException
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI

class CountDownPresenter(private val view: CountDownContract.View,
                         private val timer: TimerI,
                         private val stringFetcher: StringFetcherI,
                         private val valueFormatter: ValueFormatter,
                         private val logger: LoggerI):
    CountDownContract.Presenter {


    override fun startCountDown(startTimeMillis: Long, maxTimeMillis: Long) {
        timer.start(startTimeMillis, maxTimeMillis, object : TimerI.TimerCallback{
            override fun onTimeUpdate(millisRemaining: Long) {
                logger.d("onTimeUpdate", "$millisRemaining")
                view.updateTimer(valueFormatter.timeInMillisToString(millisRemaining))
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

    override fun stopCountDown(): Long {
        return try {
            timer.stop()
        }catch (ex: TimerException){
            0L
        }
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
}