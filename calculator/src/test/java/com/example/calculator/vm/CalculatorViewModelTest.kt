package com.example.calculator.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.calculator.Constants.INVALID_EXPRESSION_WARNING
import com.example.calculator.Constants.LARGE_POWER_WARNING
import com.example.calculator.Constants.LONG_EXPRESSION_WARNING
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
        viewModel.insertComma(13, 16)
        assertEquals("5.45×54.35^1+0.", viewModel.currentOperationString.value)
    }

    @Test
    fun clear() {
        viewModel.clear()
        assertEquals(viewModel.currentOperationString.value,viewModel.currentResult.value)
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
        !! In input string only numbers 0-9 and symbols +,×,÷,^ and - are allowed
        Also, start and end selection have to be 0 in start string, and
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
    fun viewModelGetSelectionStart_enteringOperationAfterPlus_plusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545+",0,0)
        viewModel.insertOperation("×",4,4)
        assertEquals("545×",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationAfterMultiply_multiplyIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545×",0,0)
        viewModel.insertOperation("+",4,4)
        assertEquals("545+",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationAfterDivision_divisionIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545÷",0,0)
        viewModel.insertOperation("^",4,4)
        assertEquals("545^",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationAfterPower_powerIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545^",0,0)
        viewModel.insertOperation("÷",4,4)
        assertEquals("545÷",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationAfterMinus_minusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545-",0,0)
        viewModel.insertOperation("÷",4,4)
        assertEquals("545÷",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationBeforePlus_plusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545+",0,0)
        viewModel.insertOperation("×",3,3)
        assertEquals("545×",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationBeforeMultiply_multiplyIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545×",0,0)
        viewModel.insertOperation("+",3,3)
        assertEquals("545+",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationBeforeDivision_divisionIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545÷",0,0)
        viewModel.insertOperation("^",3,3)
        assertEquals("545^",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationBeforePower_powerIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545^",0,0)
        viewModel.insertOperation("÷",3,3)
        assertEquals("545÷",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringOperationBeforeMinus_minusIsReplacedByEnteredSymbol()
    {
        viewModel.insert("545-",0,0)
        viewModel.insertOperation("÷",3,3)
        assertEquals("545÷",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBeforePlus_plusIsReplacedByMinus()
    {
        viewModel.insert("545+792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBeforeMultiply_multiplyIsReplacedByMinus()
    {
        viewModel.insert("545×792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBeforeDivision_divisionIsReplacedByMinus()
    {
        viewModel.insert("545÷792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBeforePower_powerIsReplacedByMinus()
    {
        viewModel.insert("545^792",0,0)
        viewModel.insertMinus(3,3)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterPlus_plusIsReplacedByMinus()
    {
        viewModel.insert("545+792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterMultiply_minusIsJustEntered()
    {
        viewModel.insert("545×792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545×-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterDivision_minusIsJustEntered()
    {
        viewModel.insert("545÷792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545÷-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterPower_minusIsJustEntered()
    {
        viewModel.insert("545^792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545^-792",viewModel.currentOperationString.value)
        assertEquals(5,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBetweenMultiplyAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBetweenDivisionAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545÷-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusBetweenPowerAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(4,4)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterMultiplyAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterDivisionAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545÷-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringMinusAfterPowerAndMinus_oneMinusIsLeft()
    {
        viewModel.insert("545^-792",0,0)
        viewModel.insertMinus(5,5)
        assertEquals("545-792",viewModel.currentOperationString.value)
        assertEquals(4,viewModel.selectionStart.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringTooPowerValue_largePowerWarning()
    {
        viewModel.insert("4^1000",0,0)
        assertEquals(LARGE_POWER_WARNING,viewModel.snackbarMessage.value)
    }

    @Test
    fun viewModelGetSelectionStart_enteringPowerInsideValue_largePowerWarning()
    {
        viewModel.insert("12451000",0,0)
        viewModel.insertOperation("^",4,4)
        assertEquals(LARGE_POWER_WARNING,viewModel.snackbarMessage.value)
    }

    @Test
    fun viewModelGetSelectionStart_zeroDivision_invalidExpressionWarning()
    {
        viewModel.insert("489÷0",0,0)
        assertEquals(INVALID_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
        assertEquals("",viewModel.currentResult .value)
    }

    @Test
    fun viewModelGetSelectionStart_commaDivision_invalidExpressionWarning()
    {
        viewModel.insert("489÷.",0,0)
        assertEquals(INVALID_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
        assertEquals("",viewModel.currentResult .value)
    }

    @Test
    fun viewModelGetSelectionStart_tooLongExpression_longExpressionWarning()
    {
        viewModel.insert("1234567890+1234567890-123456789÷123456789^123-1853",0,0)
        viewModel.insert("4",0,0)
        assertEquals(LONG_EXPRESSION_WARNING,viewModel.snackbarMessage.value)
    }

    @Test
    // need a check
    fun viewModelGetSelectionStart_severalCommas_invalidExpressionWarning()
    {
        viewModel.insert("49.",0,0)
        viewModel.insertComma(3,3)
        assertEquals("49.",viewModel.currentOperationString.value)
    }

    @Test
    // need a check
    fun viewModelGetSelectionStart_singleComma_invalidExpressionWarning()
    {
        viewModel.insert("49+.-32",0,0)
        assertEquals("",viewModel.currentResult.value)
    }

    @Test
    // some positive and obvious tests
    fun viewModelGetSelectionStart_sumOfPosAndNeg()
    {
        viewModel.insert("489+-50",0,0)
        assertEquals("439.0",viewModel.currentResult.value)
    }

    @Test
    fun viewModelGetSelectionStart_zeroPower()
    {
        viewModel.insert("489^0",0,0)
        assertEquals("1.0",viewModel.currentResult.value)
    }

    @Test
    fun viewModelGetSelectionStart_sumOfNegAndNeg()
    {
        viewModel.insert("-489+-50",0,0)
        assertEquals("-539.0",viewModel.currentResult.value)
    }

    @Test
    fun viewModelGetSelectionStart_division_fractionalNumber()
    {
        viewModel.insert("1÷3",0,0)
        assertEquals("0.3333333333333333",viewModel.currentResult.value)
    }

    @Test
    fun viewModelGetSelectionStart_division_minus()
    {
        viewModel.insert("1÷",0,0)
        viewModel.insert("-",2,2)
        assertEquals("1÷-",viewModel.currentResult.value)
    }

    @Test
    fun viewModelGetSelectionStart_multiply_minus()
    {
        viewModel.insert("1×",0,0)
        viewModel.insert("-",2,2)
        assertEquals("1×-",viewModel.currentResult.value)
    }

}