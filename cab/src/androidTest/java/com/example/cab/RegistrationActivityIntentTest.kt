package com.example.cab

import android.content.ComponentName
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cab.activities.map.UserDataActivity
import com.example.cab.activities.registration.RegistrationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationActivityIntentTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(RegistrationActivity::class.java)

    @Test
    fun checkCorrectData() {
        inputName("qqq")
        inputSurname("qqq")
        inputPhone("123456789000")
        clickOnRegistrationButton()
        Intents.intended(
            IntentMatchers.hasComponent(
                ComponentName(
                    intentsTestRule.activity.baseContext, UserDataActivity::class.java
                )
            ))
    }

    private fun clickOnRegistrationButton() {
        Espresso.onView(ViewMatchers.withId(R.id.registration)).perform(ViewActions.click())
    }

    private fun inputName(name: String) {
        Espresso.onView(ViewMatchers.withId(R.id.nameField)).perform(ViewActions.typeText(name))
        Espresso.closeSoftKeyboard()
    }

    private fun inputSurname(surname: String) {
        Espresso.onView(ViewMatchers.withId(R.id.surnameField)).perform(ViewActions.typeText(surname))
        Espresso.closeSoftKeyboard()
    }

    private fun inputPhone(phone: String) {
        Espresso.onView(ViewMatchers.withId(R.id.phoneField)).perform(ViewActions.typeText(phone))
        Espresso.closeSoftKeyboard()
    }
}