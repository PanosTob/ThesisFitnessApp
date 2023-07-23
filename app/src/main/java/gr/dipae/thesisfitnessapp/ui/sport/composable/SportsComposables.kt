package gr.dipae.thesisfitnessapp.ui.sport.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gr.dipae.thesisfitnessapp.ui.sport.model.SportsUiState
import gr.dipae.thesisfitnessapp.ui.sport.navigation.OnSportSelected
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@Composable
fun SportsContent(
    uiState: SportsUiState,
    onSportSelected: OnSportSelected = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp),
        state = rememberLazyGridState(),
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp),
        horizontalArrangement = Arrangement.spacedBy(SpacingHalf_8dp)
    ) {
        items(items = uiState.sports, key = { item -> item.id }) { item ->
            SportItem(item) { onSportSelected(it) }
        }
    }
}