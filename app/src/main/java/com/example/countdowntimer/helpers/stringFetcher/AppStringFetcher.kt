package com.example.countdowntimer.helpers.stringFetcher

import android.content.Context

class AppStringFetcher(private val context: Context): StringFetcherI {

    override fun getString(resId: Int): String {
        return context.resources.getString(resId)
    }
}