package com.example.counter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// displays main UI for the counter app
@Composable
fun CounterScreen(
    vm: CounterViewModel = viewModel(),
    onOpenSettings: () -> Unit
) {
    // get the current state from the view model using StateFlow
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // current counter value
        Text("Count: ${state.count}", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Row {
            // buttons hooked up to their corresponding functions
            Button(onClick = { vm.decrement() }) { Text("-1") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { vm.reset() }) { Text("Reset") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { vm.increment() }) { Text("+1") }
        }
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            // auto mode toggle which toggles auto mode on and off
            Text("Auto mode: ${if (state.autoMode) "ON" else "OFF"}")
            Spacer(Modifier.width(8.dp))
            Switch(checked = state.autoMode, onCheckedChange = { vm.toggleAutoMode() })
        }
        Spacer(Modifier.height(16.dp))

        // button to open the settings screen
        Button(onClick = onOpenSettings) { Text("Settings") }
    }
}
