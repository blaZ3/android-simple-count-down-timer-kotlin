package com.example.countdowntimer.app.countDownScreen


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.countdowntimer.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class CountDownActivityUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(CountDownActivity::class.java)

    @Test
    fun check_if_all_view_elements_are_shown() {
        Thread.sleep(100)
        onView(withId(R.id.txtTimer)).check(matches(isDisplayed()))
        onView(withId(R.id.btnIncrementTimer)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_toast_when_increment_clicked() {
        Thread.sleep(100)
        onView(withId(R.id.btnIncrementTimer)).perform(ViewActions.click())
        onView(withText(R.string.str_time_changed)).inRoot(withDecorView(not(`is`(mActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }
}
