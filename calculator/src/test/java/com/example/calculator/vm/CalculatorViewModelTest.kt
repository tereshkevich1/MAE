package com.example.calculator.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mariuszgromada.math.mxparser.License
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculatorViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun init(){
        License.iConfirmNonCommercialUse("Nikita Kurganovich")
        viewModel  =  CalculatorViewModel()
    }

    @Test
    fun onCleared() {
    }


    @Test
    fun clear() {
        viewModel.clear()
        assertEquals(viewModel.currentOperationString.value,viewModel.currentResult.value)
    }

    @Test
    fun getCurrentOperationString() {
    }


    //thingUnderTest_TriggerOfTest_ResultOfTest notation
    @Test
    fun viewModelGetSelectionStart_enteringZeroAtEmptyString_selectionIsSetTo1() {
        // To test behavior we need to set up start string
        viewModel.insert("0",0,0)
        assertEquals(1, viewModel.selectionStart.value)
        // Test have to pass successfully
    }

    @Test
    fun viewModelGetSelectionStart_enteringNumberInMiddleOfString_selectionIsMovedTo4Position(){
        /*
        !! In input string only numbers 0-9 and symbols +,×,/,^ and - are allowed
        Also, start and end selection have to be 0 in start sting and
        they can't be larger than size of current string or < 0
         */
        viewModel.insert("545×5435^1+5",0,0)
        // Selection starts with 0 (pretty obvious, but anyway)
        viewModel.insert("9",3,3)
        // Check if output sting is correct
        assertEquals("5459×5435^1+5",viewModel.currentOperationString.value)
        // Check if selection is correct
        assertEquals(4,viewModel.selectionStart.value)
        // This test also have to pass successfully
        // Ask questions in TG, if there are any. Good luck, V
    }

    @Test
    fun getSelectionEnd() {
    }

    @Test
    fun getCurrentResult() {
    }

    @Test
    fun getSnackbarMessage() {
    }

    @Test
    fun insert() {
    }

    @Test
    fun insertComma() {
    }

    @Test
    fun insertOperation() {
    }

    @Test
    fun insertMinus() {
    }

    @Test
    fun evaluate() {
    }

    @Test
    fun testClear() {
    }
}