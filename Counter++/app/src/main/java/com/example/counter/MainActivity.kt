package com.example.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: CounterViewModel = viewModel()
            // state for showing the settings screen
            var showSettings by remember { mutableStateOf(false) }

            // show the setting sscreen if showSettings is true, otherwise show the counter screen
            if (showSettings) {
                SettingsScreen(vm = vm, onBack = { showSettings = false })
            } else {
                CounterScreen(vm = vm, onOpenSettings = { showSettings = true })
            }
        }
    }
}