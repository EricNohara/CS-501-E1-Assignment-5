package com.example.lifetracker

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

// the fields for a single lifecycle event
data class LifecycleEvent(
    val name: String,
    val timestamp: String,
    val color: androidx.compose.ui.graphics.Color
)

// stores and manages the lifecycle event log
class LifeTrackerViewModel: ViewModel() {
    // stateful list holding all lifecycle events
    val eventLog = mutableStateListOf<LifecycleEvent>()

    // adds new lifecycle event to the log
    fun addEvent(name: String, color: androidx.compose.ui.graphics.Color) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        eventLog.add(0, LifecycleEvent(name, timestamp, color))
    }
}