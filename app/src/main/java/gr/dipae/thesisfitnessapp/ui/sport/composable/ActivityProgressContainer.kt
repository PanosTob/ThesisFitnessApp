package gr.dipae.thesisfitnessapp.ui.sport.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gr.dipae.thesisfitnessapp.ui.theme.ColorLoaderBackground

@Composable
fun ActivityProgressContainer(
    progressDisplayed: Boolean,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        content()
        if (progressDisplayed) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorLoaderBackground),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}