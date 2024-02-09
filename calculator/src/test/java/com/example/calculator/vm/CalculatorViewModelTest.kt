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

    //TODO need to rewrite this test
    @Test
    fun canInsertComma(){
        viewModel.insert("545×5435^1+5",0,0)

        viewModel.insertComma(1, 1)
        assertEquals("5.45×5435^1+5", viewModel.currentOperationString.value)
        viewModel.insertComma(0, 0)
        assertEquals("5.45×5435^1+5", viewModel.currentOperationString.value)

        viewModel.clear()
        viewModel.insert("545×54.35^1+5878",0,0)

        viewModel.insertComma(1, 1)
        assertEquals("5.45×54.35^1+5878", viewModel.currentOperationString.value)
        viewModel.insertComma(0, 0)
        assertEquals("5.45×54.35^1+5878", viewModel.currentOperationString.value)
        viewModel.insertComma(15, 17)
        assertEquals("5.45×54.35^1+58.", viewModel.currentOperationString.value)
        viewModel.insertComma(13, 15)
        assertEquals("5.45×54.35^1+58.", viewModel.currentOperationString.value)
        viewModel.insertComma(13, 16)
        assertEquals("5.45×54.35^1+.", viewModel.currentOperationString.value)
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

    fun viewModelGetSelectionStart_enteringOperationAfterPlus_plusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545+",0,0)
        viewModel.insertOperation("×",4,4)
        assertEquals("545×",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationAfterMultiply_multiplyIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545×",0,0)
        viewModel.insertOperation("+",4,4)
        assertEquals("545+",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationAfterDivision_divisionIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545/",0,0)
        viewModel.insertOperation("^",4,4)
        assertEquals("545^",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationAfterPower_powerIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545^",0,0)
        viewModel.insertOperation("/",4,4)
        assertEquals("545/",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationAfterMinus_minusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545-",0,0)
        viewModel.insertOperation("/",4,4)
        assertEquals("545/",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationBeforePlus_plusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545+",0,0)
        viewModel.insertOperation("×",3,3)
        assertEquals("545×",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationBeforeMultiply_multiplyIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545×",0,0)
        viewModel.insertOperation("+",3,3)
        assertEquals("545+",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationBeforeDivision_divisionIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545/",0,0)
        viewModel.insertOperation("^",3,3)
        assertEquals("545^",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationBeforePower_powerIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545^",0,0)
        viewModel.insertOperation("/",3,3)
        assertEquals("545/",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringOperationBeforeMinus_minusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545-",0,0)
        viewModel.insertOperation("/",3,3)
        assertEquals("545/",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBeforePlus_plusIsReplacedByMinus()
    {
        viewModel.insert("545+792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBeforeMultiply_multiplyIsReplacedByMinus()
    {
        viewModel.insert("545×792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBeforeDivision_divisionIsReplacedByMinus()
    {
        viewModel.insert("545/792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBeforePower_powerIsReplacedByMinus()
    {
        viewModel.insert("545^792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterPlus_plusIsReplacedByMinus()
    {
        viewModel.insert("545+792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterMultiply_minusIsJustEntered()
    {
        viewModel.insert("545×792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545×-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterDivision_minusIsJustEntered()
    {
        viewModel.insert("545/792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545/-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterPower_minusIsJustEntered()
    {
        viewModel.insert("545^792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545^-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBetweenMultiplyAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBetweenDivisionAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545/-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusBetweenPowerAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterMultiplyAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterDivisionAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545/-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringMinusAfterPowerAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    fun viewModelGetSelectionStart_enteringTooPowerValue_largePowerWarning()
    {
        viewModel.insert("4^1000",0,0)
        assertEquals(LARGE_POWER_WARNING,viewModel.snackbarMessage.value)
    }

    fun viewModelGetSelectionStart_enteringPowerInsideValue_largePowerWarning()
    {
        viewModel.insert("12451000",0,0)
        viewModel.insertOperation("^",4,4)
        assertEquals(LARGE_POWER_WARNING,viewModel.snackbarMessage.value)
    }

    fun viewModelGetSelectionStart_zeroDivision_invalidExpressionWarning()
    {
        viewModel.insert("489/0",0,0)
        assertEquals(INVALID_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
        assertEquals("",viewModel.currentResult .value)
    }

    fun viewModelGetSelectionStart_commaDivision_invalidExpressionWarning()
    {
        viewModel.insert("489/.",0,0)
        assertEquals(INVALID_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
        assertEquals("",viewModel.currentResult .value)
    }

    fun viewModelGetSelectionStart_tooLongExpression_longExpressionWarning()
    {
        viewModel.insert("1234567890+1234567890-123456789/123456789^123-13",0,0)
        assertEquals(LONG_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
    }

    fun viewModelGetSelectionStart_severalZeros_multipleZerosWarning()
    {
        viewModel.insert("00",0,0)
        assertEquals(MULTIPLE_ZEROS_WARNING,viewModel.snackbarMessage.value)
    }

    // need a check
    fun viewModelGetSelectionStart_severalСommas_invalidExpressionWarning()
    {
        viewModel.insert("49.",0,0)
        viewModel.insertСomma(3,3)
        assertEquals("49.",viewModel.selectionStart.value)
    }

    // need a check
    fun viewModelGetSelectionStart_singleComma_invalidExpressionWarning()
    {
        viewModel.insert("49+.-32",0,0)
        assertEquals("17",viewModel.currentResult.value)
    }

    // some positive and obvious tests
    fun viewModelGetSelectionStart_sumOfPosAndNeg()
    {
        viewModel.insert("489+-50",0,0)
        assertEquals("439",viewModel.currentResult.value)
    }

    fun viewModelGetSelectionStart_zeroPower()
    {
        viewModel.insert("489^0",0,0)
        assertEquals("1",viewModel.currentResult.value)
    }

    fun viewModelGetSelectionStart_sumOfNegAndNeg()
    {
        viewModel.insert("-489+-50",0,0)
        assertEquals("-539",viewModel.currentResult.value)
    }

    // need a check
    fun viewModelGetSelectionStart_division_fractionalNumber()
    {
        viewModel.insert("1/3",0,0)
        assertEquals("0.333333333",viewModel.currentResult.value)
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