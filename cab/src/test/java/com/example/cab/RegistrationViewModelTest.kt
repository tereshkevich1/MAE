package com.example.cab


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.cab.activities.registration.vm.RegistrationActivityViewModel
import com.example.cab.activities.resultingInformation.vm.ResultingInformationViewModel
import com.example.cab.model.UserData
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var registrationViewModel: RegistrationActivityViewModel

    private lateinit var resultingViewModel: ResultingInformationViewModel

    @Before
    fun init() {
        registrationViewModel = RegistrationActivityViewModel()
        resultingViewModel = ResultingInformationViewModel(719f, UserData("dd", "df", "123456789000"))
    }

    //TODO need to rewrite this test

    @Test
    fun checkNameEmpty() {
        registrationViewModel.name.value = ""
        registrationViewModel.validationCheck()
        assertEquals(R.string.empty_warning, registrationViewModel.nameValidator.message)
    }

    @Test
    fun checkDistanceToShow() {
        assertEquals("719.0 m", resultingViewModel.distanceToShow)
    }

    @Test
    fun checkTimeToShow() {
        assertEquals("11 minutes", resultingViewModel.timeToShow)
    }

    @Test
    fun checkPriceToShow() {
        assertEquals("5.18 bunov", resultingViewModel.priceToShow)
    }

    @Test
    fun checkClient() {
        assertEquals("df dd", resultingViewModel.client)
    }

    @Test
    fun checkNameLength() {
        registrationViewModel.name.value = "qwertyuiopasdfghjklzxcvbnmqwert"
        registrationViewModel.validationCheck()
        assertEquals(R.string.name_length_warning, registrationViewModel.nameValidator.message)
    }

    @Test
    fun checkNameNumbers() {
        registrationViewModel.name.value = "1"
        registrationViewModel.validationCheck()
        assertEquals(R.string.invalid_characters_warning, registrationViewModel.nameValidator.message)
    }

    @Test
    fun checkCorrectName() {
        registrationViewModel.name.value = "rrr"
        registrationViewModel.validationCheck()
        assertEquals(true, registrationViewModel.nameValidator.isValid)
    }

    @Test
    fun checkSurnameEmpty() {
        registrationViewModel.surname.value = ""
        registrationViewModel.validationCheck()
        assertEquals(R.string.empty_warning, registrationViewModel.surnameValidator.message)
    }

    @Test
    fun checkSurnameLength() {
        registrationViewModel.surname.value = "qwertyuiopasdfghjklzxcvbnmqwertqwertyuiopasdfghjklz"
        registrationViewModel.validationCheck()
        assertEquals(R.string.surname_length_warning, registrationViewModel.surnameValidator.message)
    }

    @Test
    fun checkSurnameNumbers() {
        registrationViewModel.surname.value = "1"
        registrationViewModel.validationCheck()
        assertEquals(R.string.invalid_characters_warning, registrationViewModel.surnameValidator.message)
    }

    @Test
    fun checkCorrectSurname() {
        registrationViewModel.surname.value = "rrr"
        registrationViewModel.validationCheck()
        assertEquals(true, registrationViewModel.surnameValidator.isValid)
    }

    @Test
    fun checkPhoneEmpty() {
        registrationViewModel.phone.value = ""
        registrationViewModel.validationCheck()
        assertEquals(R.string.empty_warning, registrationViewModel.phoneValidator.message)
    }




}