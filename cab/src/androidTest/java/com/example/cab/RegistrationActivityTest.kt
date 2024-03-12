package com.example.cab

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cab.activities.registration.RegistrationActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 * @property activityRule [ActivityScenarioRule] for test
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RegistrationActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(RegistrationActivity::class.java)


    @Before
    fun clear(){
        onView(withId(R.id.phoneField)).perform(clearText())
        onView(withId(R.id.nameField)).perform(clearText())
        onView(withId(R.id.surnameField)).perform(clearText())
    }
    @Test
    fun useAppContext() {
        /*

         */
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cab", appContext.packageName)
    }

    @Test
    fun checkNameEmpty() {
        inputName("")
        clickOnRegistrationButton()
        onView(withId(R.id.nameField)).check(matches(hasErrorText("This field can't be empty")))
    }

    @Test
    fun checkNameLength() {
        inputName("qwertyuiopasdfghjklzxcvbnmqwert")
        clickOnRegistrationButton()
        onView(withId(R.id.nameField)).check(matches(hasErrorText("Name is too long!")))
    }
    @Test
    fun checkNameNumbers() {
        inputName("5")
        clickOnRegistrationButton()
        onView(withId(R.id.nameField)).check(matches(hasErrorText("Only letters allowed!")))
    }
    @Test
    fun checkNameSymbols() {
        inputName("!")
        clickOnRegistrationButton()
        onView(withId(R.id.nameField)).check(matches(hasErrorText("Only letters allowed!")))
    }

    @Test
    fun checkSurnameEmpty() {
        inputSurname("")
        clickOnRegistrationButton()
        onView(withId(R.id.surnameField)).check(matches(hasErrorText("This field can't be empty")))
    }

    @Test
    fun checkSurnameLength() {
        inputSurname("qwertyuiopasdfghjklzxcvbnmqwertqwertyuiopasdfghjklz")
        clickOnRegistrationButton()
        onView(withId(R.id.surnameField)).check(matches(hasErrorText("Surname is too long!")))
    }
    @Test
    fun checkSurnameNumbers() {
        inputSurname("5")
        clickOnRegistrationButton()
        onView(withId(R.id.surnameField)).check(matches(hasErrorText("Only letters allowed!")))
    }
    @Test
    fun checkSurnameSymbols() {
        inputSurname("!")
        clickOnRegistrationButton()
        onView(withId(R.id.surnameField)).check(matches(hasErrorText("Only letters allowed!")))
    }

    @Test
    fun checkPhoneEmpty() {
        inputPhone("")
        clickOnRegistrationButton()
        onView(withId(R.id.phoneField)).check(matches(hasErrorText("Incorrect phone number format!")))
    }

    @Test
    fun checkPhoneTooLongLength() {
        inputPhone("1234567890000")
        clickOnRegistrationButton()
        onView(withId(R.id.phoneField)).check(matches(withText("+123 45 678 90 00")))
    }

    @Test
    fun checkPhoneTooShortLength() {
        inputPhone("123456789")
        clickOnRegistrationButton()
        onView(withId(R.id.phoneField)).check(matches(hasErrorText("Incorrect phone number format!")))
    }
    @Test
    fun checkPhoneSymbols() {
        inputPhone(".")
        clickOnRegistrationButton()
        onView(withId(R.id.phoneField)).check(matches(withText("+")))
    }



    private fun clickOnRegistrationButton() {
        onView(withId(R.id.registration)).perform(click())
    }

    private fun inputName(name: String) {
        onView(withId(R.id.nameField)).perform(typeText(name))
        closeSoftKeyboard()
    }

    private fun inputSurname(surname: String) {
        onView(withId(R.id.surnameField)).perform(typeText(surname))
        closeSoftKeyboard()
    }

    private fun inputPhone(phone: String) {
        onView(withId(R.id.phoneField)).perform(typeText(phone))
        closeSoftKeyboard()
    }
}