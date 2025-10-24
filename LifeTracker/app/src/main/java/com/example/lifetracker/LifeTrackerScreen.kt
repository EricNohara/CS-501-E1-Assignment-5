package com.example.lifetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lifetracker.LifeTrackerViewModel
import kotlinx.coroutines.launch

@Composable
fun LifeTrackerScreen(viewModel: LifeTrackerViewModel) {
    // list of lifecycle events
    val events = viewModel.eventLog

    // use to keep track of currently visible snackbar messages
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
        {
            // heading
            Text(
                "Lifecycle Events",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            // lazy column containing the events
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // add the events with their names and their status colors
                items(events) { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(event.color.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = "${event.timestamp} — ${event.name}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }

    // show the snackbar when the event list changes
    if (events.isNotEmpty()) {
        val lastEvent = events.first()

        LaunchedEffect(events.size) { // runs when a new event is added
            snackbarHostState.showSnackbar("Event: ${lastEvent.name}")
        }
    }
}