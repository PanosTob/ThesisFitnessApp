package gr.dipae.thesisfitnessapp.ui.sport.customize.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp

@Composable
fun SportCustomizeContent(
    uiState: SportCustomizeUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp),
    ) {
        uiState.sport?.let {
            ThesisFitnessHLText(text = it.name, fontSize = 24.sp)
        }
    }
}