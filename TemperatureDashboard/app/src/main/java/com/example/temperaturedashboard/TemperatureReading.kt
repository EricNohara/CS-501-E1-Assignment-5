package com.example.temperaturedashboard

import java.text.SimpleDateFormat
import java.util.*

// class used for storing the temperature reading
class TemperatureReading(
    val timestamp: Long, // store epoch ms tiume
    val value: Float
) {
    // used to format the timestamp into a readable string
    fun formattedTime(): String {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
}
