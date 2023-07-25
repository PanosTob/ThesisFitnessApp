package gr.dipae.thesisfitnessapp.ui.sport.customize.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportParameterUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondaryDark
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

internal typealias OnStartClicked = () -> Unit

@Composable
fun SportCustomizeContent(
    uiState: SportCustomizeUiState,
    onStartClicked: OnStartClicked
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)) {
            ThesisFitnessHLText(text = uiState.name, fontSize = 24.sp)
            SportCustomizeParametersDropdown(
                uiState.parameters,
                uiState.selectedParameter.value,
                onParameterSelection = { uiState.onParameterSelection(it) }
            )
            SportParameterEditText(uiState.selectedParameter.value, Modifier.fillMaxWidth(0.5f))
        }
        ThesisFitnessHLText(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f)
                .background(ColorSecondaryDark, RoundedCornerShape(SpacingDefault_16dp))
                .clickable { onStartClicked() }
                .padding(SpacingHalf_8dp),
            text = stringResource(R.string.sport_start_button),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun SportParameterEditText(parameter: SportParameterUiItem?, modifier: Modifier = Modifier) {
    parameter?.let {
        var parameterValueText by remember { mutableStateOf(TextFieldValue(parameter.parameterValue.value)) }
        TextField(
            modifier = modifier,
            value = parameterValueText,
            onValueChange = {
                parameterValueText = it
                parameter.parameterValue.value = it.text
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportCustomizeParametersDropdown(
    sportParameters: List<SportParameterUiItem>,
    selectedParameter: SportParameterUiItem?,
    onParameterSelection: (SportParameterUiItem) -> Unit
) {
    if (sportParameters.isNotEmpty()) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedParameter?.name ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                sportParameters.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        text = { ThesisFitnessHLText(text = it.name, fontSize = 18.sp, color = Color.Black) },
                        onClick = {
                            expanded = false
                            onParameterSelection(it)
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            leadingIconColor = MaterialTheme.colorScheme.primary,

                            ),
                        leadingIcon = {
                            Icon(
                                painterResource(id = it.iconRes),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    )
                }
            }
        }
    }
}