package gr.dipae.thesisfitnessapp.ui.sport.customize.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.navigation.OnStartClicked
import gr.dipae.thesisfitnessapp.ui.sport.model.SportParameterUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondaryDark
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@Composable
fun SportCustomizeContent(
    uiState: SportCustomizeUiState,
    onStartClicked: OnStartClicked
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)) {
            ThesisFitnessHLText(text = uiState.name, fontSize = 24.sp)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacingDefault_16dp)
            ) {
                SportCustomizeParametersDropdown(
                    uiState.parameters,
                    Modifier.weight(0.5f),
                    onParameterSelection = { uiState.onParameterSelection(it) }
                )
                SportParameterEditText(uiState.selectedParameter.value, Modifier.weight(0.5f))
            }
        }
        ThesisFitnessHLText(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f)
                .background(ColorSecondaryDark, RoundedCornerShape(SpacingDefault_16dp))
                .clickable { onStartClicked(uiState.sportId) }
                .padding(SpacingHalf_8dp),
            text = stringResource(R.string.sport_start_button),
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SportParameterEditText(parameter: SportParameterUiItem?, modifier: Modifier = Modifier) {
    parameter?.let {
        TextField(
            modifier = modifier
                .aspectRatio(5f),
            value = TextFieldValue(parameter.parameterValue.value),
            onValueChange = { parameter.parameterValue.value = it.text }
        )
    }
}

@Composable
fun SportCustomizeParametersDropdown(
    sportParameters: List<SportParameterUiItem>,
    modifier: Modifier = Modifier,
    onParameterSelection: (SportParameterUiItem) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    DropdownMenu(modifier = modifier
        .background(ColorSecondaryDark)
        .clickable { expanded != expanded }, expanded = expanded, onDismissRequest = { expanded = false }) {
        sportParameters.forEach {
            DropdownMenuItem(
                text = { ThesisFitnessHLText(text = it.name, fontSize = 18.sp) },
                onClick = {
                    expanded = false
                    onParameterSelection(it)
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = null
                    )
                }
            )
        }
    }
}