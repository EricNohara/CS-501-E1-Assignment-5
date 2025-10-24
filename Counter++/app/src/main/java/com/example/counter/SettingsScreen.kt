package com.example.counter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    vm: CounterViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    var input by remember { mutableStateOf(state.intervalSeconds.toString()) }

    // settings menu where user can enter new auto increment interval
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Auto Increment Interval (seconds)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        TextField(
            value = input,
            onValueChange = { input = it },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        Row {
            // save button calls the set interval seconds function on the parsed input, then returns to previous screen
            Button(onClick = {
                input.toDoubleOrNull()?.let { vm.setIntervalSeconds(it) }
                onBack()
            }) { Text("Save") }
            Spacer(Modifier.width(8.dp))
            // cancel button takes user back to previous screen
            Button(onClick = onBack) { Text("Cancel") }
        }
    }
}
