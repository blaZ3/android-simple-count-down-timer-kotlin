package com.example.countdowntimer

import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class ValueFormatterTests {

    private lateinit var stringFetcher: StringFetcherI
    private lateinit var valueFormatter: ValueFormatter

    private val STR_DONE = "DONE"

    private val MINUTE = 60
    private val SECOND = 1

    @Before
    fun setup(){
        stringFetcher = mock()
        valueFormatter = ValueFormatter(stringFetcher)
    }


    @Test
    fun `when `(){
        whenever(stringFetcher.getString(R.string.str_time_done)).thenReturn(STR_DONE)

        assert(valueFormatter.timeInMillisToString(0) == STR_DONE)
        assert(valueFormatter.timeInMillisToString((1*MINUTE + 30*SECOND)*1000L) == "1 : 30 : 0")
        assert(valueFormatter.timeInMillisToString(((1*MINUTE + 0*SECOND)*1000L)+300) == "1 : 0 : 3")
        assert(valueFormatter.timeInMillisToString(((0*MINUTE + 15*SECOND)*1000L)+600) == "15 : 6")
        assert(valueFormatter.timeInMillisToString(-1000) == STR_DONE)

    }

}