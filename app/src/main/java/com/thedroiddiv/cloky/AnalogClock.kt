package com.thedroiddiv.cloky

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    hourHandColor: Color = Color.White,
    minuteHandColor: Color = Color.White,
    secondHandColor: Color = Color.Red,
    hourMarkerColor: Color = Color.White,
    minuteMarkerColor: Color = Color.Gray,
    hourMarkerLength: Float = 10f,
    minuteMarkerLength: Float = 5f,
    currentTime: Long,
) {
    val locale = LocalConfiguration.current.locales[0]
    val hour = remember(currentTime) {
        Calendar.getInstance(locale).get(Calendar.HOUR_OF_DAY) % 12
    }
    val minute = remember(currentTime) {
        Calendar.getInstance(locale).get(Calendar.MINUTE)
    }
    val second = remember(currentTime) {
        Calendar.getInstance(locale).get(Calendar.SECOND)
    }

    LaunchedEffect(currentTime) {
        println("$hour $minute $second")
        println("Hour Angle: ${(3 - hour) * Math.PI / 6}")
        println("Hour Angle: ${(3 - hour) * 30}")
    }

    Canvas(
        modifier = Modifier
            .size(400.dp)
            .border(2.dp, Color.Green)
            .then(modifier)
    ) {
        val radius = this.size.width / 2
        val centerX = this.center.x
        val centerY = this.center.y

        // Draw hour markers
        for (hour in 0..11) {
            val angle = (hour * 30f).toDouble()
            val x1 = centerX + radius * cos(Math.toRadians(angle)).toFloat()
            val y1 = centerY + radius * sin(Math.toRadians(angle)).toFloat()
            val x2 =
                centerX + (radius - hourMarkerLength) * cos(Math.toRadians(angle)).toFloat()
            val y2 =
                centerY + (radius - hourMarkerLength) * sin(Math.toRadians(angle)).toFloat()
            drawLine(
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                color = hourMarkerColor,
                strokeWidth = 2f
            )
        }

        // Draw minute markers
        for (minute in 0..59) {
            if (minute % 5 != 0) {
                val angle = (minute * 6f).toDouble()
                val x1 = centerX + radius * cos(Math.toRadians(angle)).toFloat()
                val y1 = centerY + radius * sin(Math.toRadians(angle)).toFloat()
                val x2 =
                    centerX + (radius - minuteMarkerLength) * cos(Math.toRadians(angle)).toFloat()
                val y2 =
                    centerY + (radius - minuteMarkerLength) * sin(Math.toRadians(angle)).toFloat()
                drawLine(
                    start = Offset(x1, y1),
                    end = Offset(x2, y2),
                    color = minuteMarkerColor,
                    strokeWidth = 1f
                )
            }
        }

        // Draw hour hand
        // val hourAngle = (hour * 30 + minute / 2f) * Math.PI / 180f - Math.PI / 4
        val hourAngle = (3 - hour) * Math.PI / 6
        val hourHandLength = radius * 0.6f
        drawLine(
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + hourHandLength * cos(hourAngle).toFloat(),
                centerY + hourHandLength * sin(hourAngle).toFloat()
            ),
            color = hourHandColor,
            strokeWidth = 4f
        )

        // Draw minute hand
        val minuteAngle = minute * Math.PI / 30f - Math.PI / 4
        val minuteHandLength = radius * 0.8f
        drawLine(
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + minuteHandLength * cos(minuteAngle).toFloat(),
                centerY + minuteHandLength * sin(minuteAngle).toFloat()
            ),
            color = minuteHandColor,
            strokeWidth = 3f
        )

        // Draw second hand (optional)
        val secondAngle = second * Math.PI / 30f - Math.PI / 4
        val secondHandLength = radius * 0.9f
        drawLine(
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + secondHandLength * cos(secondAngle).toFloat(),
                centerY + secondHandLength * sin(secondAngle).toFloat()
            ),
            color = secondHandColor,
            strokeWidth = 2f
        )
    }
}
