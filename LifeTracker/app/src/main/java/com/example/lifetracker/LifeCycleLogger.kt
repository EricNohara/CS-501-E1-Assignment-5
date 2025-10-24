package com.example.lifetracker

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.graphics.Color

// LifecycleObserver implementation that listens for the events and records them in our LifeTrackerViewModel using addEvent
class LifeCycleLogger(private val viewModel: LifeTrackerViewModel): DefaultLifecycleObserver {
    // depending on the event, add the event name and a corresponding status color to the event list
    override fun onCreate(owner: LifecycleOwner) {
        viewModel.addEvent("onCreate", Color.Blue)
    }

    override fun onStart(owner: LifecycleOwner) {
        viewModel.addEvent("onStart", Color.Green)
    }

    override fun onResume(owner: LifecycleOwner) {
        viewModel.addEvent("onResume", Color.Cyan)
    }

    override fun onPause(owner: LifecycleOwner) {
        viewModel.addEvent("onPause", Color.Yellow)
    }

    override fun onStop(owner: LifecycleOwner) {
        viewModel.addEvent("onStop", Color.Red)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        viewModel.addEvent("onDestroy", Color.Gray)
    }
}