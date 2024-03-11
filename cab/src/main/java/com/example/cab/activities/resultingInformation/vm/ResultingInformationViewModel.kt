package com.example.cab.activities.resultingInformation.vm

import androidx.lifecycle.ViewModel
import com.example.cab.model.UserData
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

class ResultingInformationViewModel(
    private val distance: Float,
    userData: UserData,
) : ViewModel() {
    val distanceToShow: String = analyzeDistance()
    val timeToShow: String = analyzeTime()
    val priceToShow: String = "${calculatePrice().roundTo(2)} bunov"
    val client: String = "${userData.userSurname} ${userData.userName}"
    private fun calculatePrice(): Double {
        val first = 0.00000000000013 * distance.pow(3)
        val second = 0.00000002018124 * distance.pow(2)
        val third = 0.00223609401847 * distance + 3.58526100225436
        return first - second + third
    }

    private fun analyzeTime(): String {
        val totalMinutes = 0.38734767167377 * distance.pow(0.45329306f)
        val days = (totalMinutes / (60 * 24)).toInt()
        val hours = ((totalMinutes % (60 * 24)) / 60).toInt()
        val minutes = (totalMinutes % 60).toInt()
        val daysString = if (days > 0) "$days days " else ""
        val hoursString = if (hours > 0) "$hours hours " else ""
        val minutesString = "$minutes minutes"
        return "$daysString$hoursString$minutesString"
    }

    private fun analyzeDistance(): String {
        return if (distance > 1000) {
            "${distance.div(1000).toDouble().roundTo(2)} km"
        } else {
            "${round(distance)} m"
        }
    }

    private fun Double.roundTo(numFractionDigits: Int): Double {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return (this * factor).roundToInt() / factor
    }
}
