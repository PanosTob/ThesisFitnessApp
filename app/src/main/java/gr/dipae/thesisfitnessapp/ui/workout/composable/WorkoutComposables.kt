package gr.dipae.thesisfitnessapp.ui.workout.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp

@Composable
fun WorkoutContent() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp)
    ) {

    }
}