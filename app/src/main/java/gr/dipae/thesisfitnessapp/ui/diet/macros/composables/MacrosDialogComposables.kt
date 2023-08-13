package gr.dipae.thesisfitnessapp.ui.diet.macros.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.ifable
import gr.dipae.thesisfitnessapp.ui.base.compose.noRippleClickable
import gr.dipae.thesisfitnessapp.ui.diet.macros.model.MacrosDialogUiState
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

internal typealias OnSaveClicked = () -> Unit

@Composable
fun MacrosDialogContent(
    uiState: MacrosDialogUiState,
    onSaveClicked: OnSaveClicked
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .noRippleClickable { localFocusManager.clearFocus() }
            .padding(SpacingDefault_16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        ThesisFitnessHLText(text = stringResource(id = R.string.diet_macros_dialog_title), fontSize = 22.sp)

        NutrientEditText(
            nutrient = uiState.calories,
            placeholder = stringResource(id = R.string.diet_macros_dialog_calories_placeholder),
            onNutrientTextChanged = { uiState.updateSaveButtonEnabledState() },
            onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
        )

        NutrientEditText(
            nutrient = uiState.protein,
            placeholder = stringResource(id = R.string.diet_macros_dialog_protein_placeholder),
            onNutrientTextChanged = { uiState.updateSaveButtonEnabledState() },
            onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
        )

        NutrientEditText(
            nutrient = uiState.carbs,
            placeholder = stringResource(id = R.string.diet_macros_dialog_carbs_placeholder),
            onNutrientTextChanged = { uiState.updateSaveButtonEnabledState() },
            onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
        )

        NutrientEditText(
            nutrient = uiState.fats,
            placeholder = stringResource(id = R.string.diet_macros_dialog_fats_placeholder),
            onNutrientTextChanged = { uiState.updateSaveButtonEnabledState() },
            onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
        )

        NutrientEditText(
            nutrient = uiState.water,
            placeholder = stringResource(id = R.string.diet_macros_dialog_water_placeholder),
            onNutrientTextChanged = { uiState.updateSaveButtonEnabledState() },
            onDone = { localFocusManager.clearFocus() }
        )

        ThesisFitnessHLText(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(4f)
                .background(uiState.saveButton.color.invoke(), RoundedCornerShape(SpacingDefault_16dp))
                .ifable(uiState.saveButton.isEnabled.value) {
                    clickable {
                        uiState.saveButton.onClick {
                            localFocusManager.clearFocus()
                            onSaveClicked()
                        }
                    }
                }
                .padding(SpacingHalf_8dp),
            text = stringResource(R.string.diet_macros_dialog_save_button),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun NutrientEditText(
    modifier: Modifier = Modifier,
    nutrient: MutableState<String>,
    placeholder: String,
    onNutrientTextChanged: (String) -> Unit,
    onDone: () -> Unit
) {
    var nutrientValueText by remember(nutrient.value) {
        mutableStateOf(
            TextFieldValue(
                text = nutrient.value,
                selection = TextRange(nutrient.value.length)
            )
        )
    }
    val validationPattern = remember { Regex("^\\d+\$") }
    TextField(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(5f),
        value = nutrientValueText,
        placeholder = {
            ThesisFitnessBLAutoSizeText(text = placeholder, maxLines = 1)
        },
        onValueChange = {
            when {
                it.text.isBlank() -> {
                    nutrientValueText = TextFieldValue("")
                    nutrient.value = ""
                }

                it.text.matches(validationPattern) -> {
                    nutrientValueText = it
                    nutrient.value = it.text
                }
            }
            onNutrientTextChanged(it.text)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}