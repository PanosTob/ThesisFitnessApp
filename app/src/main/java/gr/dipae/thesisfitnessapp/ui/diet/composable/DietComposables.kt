package gr.dipae.thesisfitnessapp.ui.diet.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp

@Composable
fun DietContent() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        var fabExpandedState by remember { mutableStateOf(false) }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { fabExpandedState = !fabExpandedState },
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}