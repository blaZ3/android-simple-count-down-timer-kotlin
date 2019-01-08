package com.example.countdowntimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.countdowntimer.countDown.CountDownContract
import com.example.countdowntimer.countDown.CountDownPresenter
import com.example.countdowntimer.timer.Timer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountDownContract.View {

    private lateinit var presenter: CountDownContract.Presenter

    private val countDownStarts: Long = 2 *60 * 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = CountDownPresenter(this, Timer())
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopCountDown()
    }


    override fun initView() {
        presenter.startCountDown(countDownStarts, MainApplication.MAX_TIME)
        btnIncrementTimer.setOnClickListener {
            presenter.incrementTimer()
        }
    }

    override fun updateTimer(timer: String) {
        txtTimer.text = timer
    }

    override fun timerDone() {
        txtTimer.text = resources.getString(R.string.str_time_done)
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
