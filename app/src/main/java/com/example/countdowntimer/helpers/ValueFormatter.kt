package com.example.countdowntimer.helpers

import com.example.countdowntimer.R
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI

class ValueFormatter(private val stringFetcher: StringFetcherI) {

    fun timeInMillisToString(millis: Long): String{
        if (millis <= 0){
            return stringFetcher.getString(R.string.str_time_done)
        }
        val minutes = (millis/1000)/60
        val seconds = (millis/1000) % 60

        return if (minutes>0){
            "$minutes : $seconds : ${(millis%1000)/100}"
        }else{
            "$seconds : ${(millis%1000)/100}"
        }
    }

}