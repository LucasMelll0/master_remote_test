package com.example.master_remote_test.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressbar(progress: Float, text: String, modifier: Modifier = Modifier) {
    val size = 200.dp
    val indicatorThickness = 20.dp
    val progressBackgroundColor = MaterialTheme.colorScheme.primary.copy(0.1f)
    val progressColor = MaterialTheme.colorScheme.primary
    val animateNumber by animateFloatAsState(targetValue = progress, animationSpec = tween())
    Box(contentAlignment = Alignment.Center, modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            drawCircle(
                color = progressBackgroundColor,
                radius = size.toPx() / 2,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
            val sweepAngle = (animateNumber / 100) * 360
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = text, style = MaterialTheme.typography.titleLarge)

    }
}