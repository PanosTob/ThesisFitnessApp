package gr.dipae.thesisfitnessapp.ui.diet.foodselection.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
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
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.ifable
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorDisabledButton
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@ExperimentalMaterial3Api
@Composable
fun FoodItemNutrientsDialog(
    selectedFoodItem: FoodUiItem?,
    onSaveClicked: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (selectedFoodItem != null) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
        ) {
            Column {
                val localFocusManager = LocalFocusManager.current
                ThesisFitnessBLAutoSizeText(text = stringResource(id = R.string.diet_food_save_description))
                VerticalSpacerDefault()

                var gramsValueText by remember {
                    mutableStateOf(
                        TextFieldValue(
                            text = "",
                            selection = TextRange(0, 6)
                        )
                    )
                }
                FoodItemGramsEditText(
                    gramsValueText = gramsValueText,
                    onTextFieldValueChanged = { gramsValueText = it },
                    onDone = { localFocusManager.clearFocus() }
                )
                VerticalSpacerDefault()

                val addButtonIsEnabled by remember(gramsValueText.text.isNotBlank()) { mutableStateOf(gramsValueText.text.isNotBlank()) }
                FoodItemNutrientsAddButton(
                    isEnabled = addButtonIsEnabled,
                    onSaveClicked = {
                        localFocusManager.clearFocus()
                        onDismiss()
                        onSaveClicked(gramsValueText.text)
                    }
                )
            }
        }
    }
}

@Composable
fun FoodItemGramsEditText(
    gramsValueText: TextFieldValue,
    onTextFieldValueChanged: (TextFieldValue) -> Unit,
    onDone: (String) -> Unit
) {
    val validationPattern = remember { Regex("^\\d{0,5}\$") }
    TextField(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(5f),
        value = gramsValueText,
        placeholder = {
            ThesisFitnessBLAutoSizeText(text = "", maxLines = 1)
        },
        onValueChange = {
            when {
                it.text.isBlank() -> {
                    onTextFieldValueChanged(TextFieldValue(""))
                }

                it.text.matches(validationPattern) -> {
                    onTextFieldValueChanged(it)
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone(gramsValueText.text) })
    )
}

@Composable
fun FoodItemNutrientsAddButton(
    isEnabled: Boolean,
    onSaveClicked: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    ThesisFitnessHLText(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .aspectRatio(4f)
            .drawBehind {
                val backgroundColor = if (isEnabled) primaryColor else ColorDisabledButton
                drawRoundRect(
                    color = backgroundColor,
                    cornerRadius = CornerRadius(SpacingDefault_16dp.toPx(), SpacingDefault_16dp.toPx())
                )
            }
            .ifable(isEnabled) {
                clickable {
                    onSaveClicked()
                }
            }
            .padding(SpacingHalf_8dp),
        text = stringResource(R.string.diet_macros_dialog_save_button),
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background
    )
}