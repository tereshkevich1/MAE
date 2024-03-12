package com.example.cab

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
<<<<<<< Updated upstream
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
=======
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
>>>>>>> Stashed changes
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cab.activities.registration.RegistrationActivity
import com.example.cab.activities.resultingInformation.ResultingInformationActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ResultingInformationActivityTest {
    @get:Rule
<<<<<<< Updated upstream
    val activityRule = ActivityScenarioRule(RegistrationActivity::class.java)

=======
    val activityRule = ActivityScenarioRule(ResultingInformationActivity::class.java)

    @Before
    fun clear(){
        onView(withId(R.id.phoneField)).perform(clearText())
        onView(withId(R.id.nameField)).perform(clearText())
        onView(withId(R.id.surnameField)).perform(clearText())
    }
>>>>>>> Stashed changes
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cab", appContext.packageName)
    }

<<<<<<< Updated upstream
    fun checkCorrectData() {
        init()
        onView(withId(R.id.call_again)).perform(click())
        intended(hasComponent(RegistrationActivity::class.java.getName()));
        release()
    }

=======

    @Test
    fun checkCorrectData() {
        init()
        onView(withId(R.id.call_again)).perform(click())
        intended(hasComponent(RegistrationActivity::class.java.getName()))
        release()
    }
>>>>>>> Stashed changes
}