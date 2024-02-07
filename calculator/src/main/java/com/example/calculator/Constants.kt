package com.example.calculator

object Constants {
    const val MAX_EXPRESSION_LENGTH = 50
    const val LARGE_POWER_THRESHOLD = 1000

    val pattern = Regex("[+×/^\\-]")
    val minusPattern = Regex("[/×^]")

    const val MULTIPLE_POINTS_WARNING = "Cannot enter multiple decimal points in one number!"
    const val OPERATION_PLACEMENT_WARNING = "Invalid operation placement!"
    const val INVALID_EXPRESSION_WARNING = "Invalid expression!"
    const val LARGE_POWER_WARNING = "Calculation error: power too large!"
    const val MULTIPLE_ZEROS_WARNING = "Cannot enter multiple zeros at the beginning of a number!"
    const val LONG_EXPRESSION_WARNING = "Expression cannot be longer than $MAX_EXPRESSION_LENGTH characters!"
}