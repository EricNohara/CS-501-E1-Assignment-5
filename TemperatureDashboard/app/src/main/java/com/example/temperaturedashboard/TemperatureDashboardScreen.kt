package com.example.temperaturedashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureDashboardScreen(vm: TemperatureViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    // skeleton of the dashboard
    Scaffold(
        // name of the app
        topBar = {
            TopAppBar(
                title = { Text("Temperature Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) {
        // add the main content of the app in a column
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Summary stats section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Summary Stats", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // display the current, average, min, and max temperatures from state
                        StatBox("Current", state.current)
                        StatBox("Average", state.average)
                        StatBox("Min", state.min)
                        StatBox("Max", state.max)
                    }
                }
            }

            // Chart display of the data
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Temperature Chart", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    TemperatureChart(readings = state.readings)
                }
            }

            // Pause/resume data button which toggles the isRunning flag in the state
            Button(
                onClick = { vm.toggleRunning() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(if (state.isRunning) "Pause Data" else "Resume Data")
            }

            // Recent readings list
            Text("Recent Readings List:", style = MaterialTheme.typography.titleMedium)
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            // lazy list of temperature readings and their timestamps
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // display the readings in chronological order
                items(state.readings.reversed()) { reading ->
                    Text(
                        text = "${"%.1f°F".format(reading.value)} - read at: ${reading.formattedTime()}",
                    )
                }
            }
        }
    }
}

// composable to display the stats for the summary section
@Composable
fun StatBox(label: String, value: Float?) {
    // display the temperature and the label underneath it
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(
            text = value?.let { "%.1f°F".format(it) } ?: "--",
            style = MaterialTheme.typography.titleLarge
        )
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}

//composable to display the chart data for the temperature readings
@Composable
fun TemperatureChart(readings: List<TemperatureReading>) {
    // only return chart if there is data to display
    if (readings.isEmpty()) return

    // use the max and min values to get the range of the chart (y axis)
    val maxTemp = readings.maxOf { it.value }
    val minTemp = readings.minOf { it.value }
    val range = maxTemp - minTemp

    // draw the chart using Canvas
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(4.dp)
    ) {
        // chart bg
        drawRect(color = Color(0xFFFFFFFF))

        // find the horizontal spacing between points on the canvas
        val stepX = size.width / (readings.size - 1).coerceAtLeast(1)

        // convert the readings into points on the canvas
        val points = readings.mapIndexed { i, r ->
            // scale temp values to fit the canvas between 0 and chart height
            val normalizedY = (r.value - minTemp) / (range.takeIf { it != 0f } ?: 1f)
            Offset(
                // Horizontal position
                x = i * stepX,
                // vertical position
                y = size.height * (1 - normalizedY)
            )
        }

        // connect points with a line for a line chart
        for (i in 0 until points.size - 1) {
            drawLine(
                color = Color(0xFF1976D2),
                start = points[i],
                end = points[i + 1],
                strokeWidth = 5f
            )
        }
    }
}

