package gr.dipae.thesisfitnessapp.ui.sport.customize.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeStartButtonUiItem
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportParameterUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

internal typealias OnStartClicked = () -> Unit
internal typealias OnClearParameterSelection = () -> Unit

@Composable
fun SportCustomizeContent(
    uiState: SportCustomizeUiState,
    onStartClicked: OnStartClicked,
    onClearParameterSelection: OnClearParameterSelection
) {
    val localFocusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures {
                    localFocusManager.clearFocus()
                }
            }
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)) {
            ThesisFitnessHLText(text = uiState.name, fontSize = 24.sp)
            SportCustomizeParametersDropdown(
                localFocusManager,
                uiState.parameters,
                uiState.selectedParameter.value,
                onParameterSelection = { uiState.onParameterSelection(it) },
                onClearParameterSelection = { onClearParameterSelection() }
            )
            SportParameterEditText(
                localFocusManager,
                uiState.selectedParameter.value,
                Modifier
                    .fillMaxWidth(0.5f)
            ) { uiState.updateStartButtonEnabledState() }
        }
        SportCustomizeStartButton(uiState.startButton) {
            localFocusManager.clearFocus()
            onStartClicked()
        }
    }
}

@Composable
fun SportCustomizeStartButton(
    startButton: SportCustomizeStartButtonUiItem,
    onStartClicked: OnStartClicked
) {
    ThesisFitnessHLText(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7f)
            .background(startButton.color.invoke(), RoundedCornerShape(SpacingDefault_16dp))
            .clickable { startButton.onClick { onStartClicked() } }
            .padding(SpacingHalf_8dp),
        text = stringResource(R.string.sport_start_button),
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
fun SportParameterEditText(
    localFocusManager: FocusManager,
    parameter: SportParameterUiItem?,
    modifier: Modifier = Modifier,
    onParameterTextChanged: (String) -> Unit
) {
    parameter?.let {
        var parameterValueText by remember(parameter) {
            mutableStateOf(
                TextFieldValue(
                    text = parameter.parameterValue.value,
                    selection = TextRange(parameter.parameterValue.value.length)
                )
            )
        }
        val validationPattern = remember { Regex("^\\d+\$") }
        TextField(
            modifier = modifier,
            value = parameterValueText,
            placeholder = { },

            onValueChange = {
                when {
                    it.text.isBlank() -> {
                        parameterValueText = TextFieldValue("")
                        parameter.parameterValue.value = ""
                    }

                    it.text.matches(validationPattern) -> {
                        parameterValueText = it
                        parameter.parameterValue.value = it.text
                    }
                }
                onParameterTextChanged(it.text)
            },
            visualTransformation = { text ->
                val annotatedString = AnnotatedString.Builder().run {
                    append(text)
                    if (text.text.isNotBlank()) {
                        append(parameter.unitSuffix)
                    }
                    toAnnotatedString()
                }
                val offsetMapping = if (text.text.isNotBlank()) {
                    object : OffsetMapping {
                        override fun originalToTransformed(offset: Int) = offset
                        override fun transformedToOriginal(offset: Int) = offset.coerceIn(0, parameterValueText.text.length)
                    }
                } else OffsetMapping.Identity

                TransformedText(annotatedString, offsetMapping)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportCustomizeParametersDropdown(
    localFocusManager: FocusManager,
    sportParameters: List<SportParameterUiItem>,
    selectedParameter: SportParameterUiItem?,
    onParameterSelection: (SportParameterUiItem) -> Unit,
    onClearParameterSelection: OnClearParameterSelection
) {
    if (sportParameters.isNotEmpty()) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(5f)
                .clickable { localFocusManager.clearFocus() },
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.small)
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), shape = MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .menuAnchor()
                    .padding(horizontal = SpacingHalf_8dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThesisFitnessHLAutoSizeText(
                    text = if (selectedParameter?.name.isNullOrBlank()) stringResource(id = R.string.sport_customize_clear_selection_box) else selectedParameter?.name ?: "",
                    maxLines = 1
                )
                Icon(
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = ""
                )
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column(Modifier.fillMaxSize()) {
                    sportParameters.forEach {
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.background),
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
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background),
                        text = { ThesisFitnessHLText(text = stringResource(id = R.string.sport_customize_clear_parameter_selection), fontSize = 18.sp, color = Color.Black) },
                        onClick = {
                            expanded = false
                            onClearParameterSelection()
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            leadingIconColor = MaterialTheme.colorScheme.primary,

                            ),
                        leadingIcon = {
                            Icon(
                                painterResource(id = R.drawable.ic_deselect),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        }
    }
}