package gr.dipae.thesisfitnessapp.ui.base.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey

@Composable
fun WidthAdjustedDivider(
    modifier: Modifier = Modifier,
    color: Color = ColorDividerGrey,
    thickness: Dp = 1.dp,
) {
    Canvas(modifier = modifier) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            strokeWidth = thickness.toPx(),
            end = Offset(size.width, 0f)
        )
    }
}