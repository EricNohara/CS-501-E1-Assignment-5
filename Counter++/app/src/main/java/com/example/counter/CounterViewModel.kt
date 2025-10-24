package com.example.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// data class for holding the counter's state
data class CounterState(
    val count: Int = 0,
    val autoMode: Boolean = false,
    val intervalSeconds: Double = 3.0
)

class CounterViewModel: ViewModel() {
    private val _state = MutableStateFlow(CounterState())
    val state: StateFlow<CounterState> = _state.asStateFlow()

    private var autoIncrementJobActive = false

    // functions to increment, decrement, reset, set interval
    fun increment() {
        _state.update { it.copy(count = it.count + 1) }
    }

    fun decrement() {
        _state.update { it.copy(count = it.count - 1) }
    }

    fun reset() {
        _state.update { it.copy(count = 0) }
    }

    fun setIntervalSeconds(seconds: Double) {
        _state.update { it.copy(intervalSeconds = seconds) }
    }

    // every n seconds, increment the count by 1
    private fun startAutoIncrement() {
        autoIncrementJobActive = true
        viewModelScope.launch {
            while (_state.value.autoMode) {
                delay((_state.value.intervalSeconds * 1000).toLong())
                _state.update { it.copy(count = it.count + 1) }
            }
            autoIncrementJobActive = false
        }
    }

    // toggle the auto mode and manually start the auto increment
    fun toggleAutoMode() {
        _state.update { it.copy(autoMode = !it.autoMode) }
        if (_state.value.autoMode && !autoIncrementJobActive) {
            startAutoIncrement()
        }
    }
}