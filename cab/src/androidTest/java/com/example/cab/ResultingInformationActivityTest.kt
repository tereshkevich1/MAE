package com.example.cab

import android.content.ComponentName
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cab.activities.registration.RegistrationActivity
import com.example.cab.activities.resultingInformation.ResultingInformationActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ResultingInformationActivityTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(ResultingInformationActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cab", appContext.packageName)
    }

    @Test
    fun checkCorrectData() {
        onView(withId(R.id.call_again)).perform(click())
        Intents.intended(
            IntentMatchers.hasComponent(
                ComponentName(
                    intentsTestRule.activity.baseContext, RegistrationActivity::class.java
                )
            ))
    }

}