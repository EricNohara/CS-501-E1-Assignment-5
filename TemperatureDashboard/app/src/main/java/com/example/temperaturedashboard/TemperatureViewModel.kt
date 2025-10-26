package com.example.temperaturedashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

// data class holding the fields needed for the dashboard state
data class DashboardState (
    val readings: List<TemperatureReading> = emptyList(),
    val isRunning: Boolean = true
) {
    val current: Float? get() = readings.lastOrNull()?.value
    val average: Float? get() = if (readings.isNotEmpty()) readings.map { it.value }.average().toFloat() else null
    val min: Float? get() = readings.minOfOrNull { it.value }
    val max: Float? get() = readings.maxOfOrNull { it.value }
}

class TemperatureViewModel: ViewModel() {
    // holds the latest dashboard state and exposes it as a flow
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private var generatorJob: Job? = null

    init {
        startGenerating()
    }

    private fun startGenerating() {
        // launch a coroutine to generate new readings every 2 seconds
        generatorJob = viewModelScope.launch {
            while (isActive) {
                if (_state.value.isRunning) {
                    // generate a new random reading
                    val newReading = TemperatureReading(
                        timestamp = System.currentTimeMillis(),
                        value = Random.nextFloat() * 20f + 65f // 65F – 85F
                    )
                    // store the last 20 readings only and copy the contents to the state
                    val updatedList = (_state.value.readings + newReading).takeLast(20)
                    _state.value = _state.value.copy(readings = updatedList)
                }
                // reading happens every 2 seconds
                delay(2000)
            }
        }
    }

    // used for pausing or resuming the data flow
    fun toggleRunning() {
        _state.value = _state.value.copy(isRunning = !_state.value.isRunning)
    }

    override fun onCleared(){
        generatorJob?.cancel()
        super.onCleared()
    }
}