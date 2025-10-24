package com.example.lifetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val viewModel: LifeTrackerViewModel by viewModels()

    // lifecycle observer that logs events to the view model
    private lateinit var logger: LifeCycleLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // create a logger observing lifecycle events and register it as a lifecycle observer so it receives callbacks
        logger = LifeCycleLogger(viewModel)
        lifecycle.addObserver(logger)

        // set the UI content to our screen
        setContent {
            LifeTrackerScreen(viewModel)
        }
    }
}