package com.example.aplikasigithubuser.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.aplikasigithubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(SplashActivity::class.java)
    }

    @Test
    fun splashActivityRedirectToMainActivity() {
        Thread.sleep(3000)
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.rvUser))
            .perform(ViewActions.click())
    }
}

