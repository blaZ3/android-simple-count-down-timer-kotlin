package com.example.countdowntimer.app.countDownScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.countdowntimer.BuildConfig
import com.example.countdowntimer.MainApplication
import com.example.countdowntimer.R
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CountDownActivity : AppCompatActivity(), CountDownContract.View {
    private var countDownStarts: Long = 2 * 60 * 1000L
    private val COUNTER_TIME: String = "COUNTER_TIME"

    private val stringFetcher: StringFetcherI by inject()
    private lateinit var presenter: CountDownContract.Presenter

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
        presenter = get { parametersOf(this, BuildConfig.DEBUG) }
        presenter.startCountDown(countDownStarts, MainApplication.MAX_TIME)
        btnIncrementTimer.setOnClickListener {
            presenter.incrementTimer()
        }
    }

    override fun updateTimer(timer: String) {
        runOnUiThread {
            txtTimer.text = timer
        }
    }

    override fun timerDone() {
        runOnUiThread {
            txtTimer.text = stringFetcher.getString(R.string.str_time_done)
        }
    }

    override fun showToast(msg: String) {
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putLong(COUNTER_TIME, presenter.getRemainingTime())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopCountDown()
    }
}
