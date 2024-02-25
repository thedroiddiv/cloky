package com.thedroiddiv.cloky

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DigitalClock(currentTime: Long) {
    val formattedTime = remember(currentTime) {
        SimpleDateFormat("hh:mm:ss aa", Locale.getDefault()).format(Date(currentTime))
    }

    Column {
        Text(text = formattedTime, style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun rememberCurrentTime(): Long {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (isActive) {
            delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }
    return currentTime
}