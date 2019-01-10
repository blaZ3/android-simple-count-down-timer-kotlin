package com.example.countdowntimer.app.countDownScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.countdowntimer.MainApplication
import com.example.countdowntimer.R
import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import kotlinx.android.synthetic.main.activity_main.*

class CountDownActivity : AppCompatActivity(), CountDownContract.View {

    private lateinit var presenter: CountDownContract.Presenter

    private var countDownStarts: Long = 2 * 60 * 1000L

    private val mainApplication: MainApplication
        get() = (application as MainApplication)

    private val logger: LoggerI
        get() = mainApplication.logger

    private val stringFetcher: StringFetcherI
        get() = mainApplication.stringFetcher

    private val COUNTER_TIME: String = "COUNTER_TIME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            if (it.containsKey(COUNTER_TIME)) {
                countDownStarts = it.getLong(COUNTER_TIME)
            }
        }

        initView()
    }

    override fun initView() {
        presenter = CountDownPresenter(
            view = this, timer = Timer(),
            logger = logger,
            stringFetcher = stringFetcher,
            valueFormatter = ValueFormatter(stringFetcher)
        )

        presenter.startCountDown(countDownStarts, MainApplication.MAX_TIME)
        btnIncrementTimer.setOnClickListener {
            presenter.incrementTimer()
        }
    }

    override fun updateTimer(timer: String) {
        txtTimer.text = timer
    }

    override fun timerDone() {
        txtTimer.text = stringFetcher.getString(R.string.str_time_done)
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putLong(COUNTER_TIME, presenter.stopCountDown())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopCountDown()
    }
}
