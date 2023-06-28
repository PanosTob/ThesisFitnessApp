package gr.dipae.thesisfitnessapp.ui.base.compose

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey

@Composable
fun DividerHorizontal(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = ColorDividerGrey,
        thickness = 1.dp
    )
}