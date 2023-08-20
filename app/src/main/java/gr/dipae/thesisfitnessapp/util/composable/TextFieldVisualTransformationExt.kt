package gr.dipae.thesisfitnessapp.util.composable

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DigitOnlyEditText(
    valueString: String,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    allowDecimals: Boolean = false,
    onTextValueChanged: (String) -> Unit
) {
    val validationPattern = remember { if (allowDecimals) Regex("^\\d+\\.?\\d*\$") else Regex("^\\d+\$") }
    var tfValue by remember(valueString) { mutableStateOf(TextFieldValue(text = valueString, selection = TextRange(valueString.length))) }
    TextField(
        modifier = modifier,
        value = tfValue,
        label = label,
        placeholder = placeholder,
        onValueChange = {
            when {
                it.text.isBlank() -> {
                    tfValue = TextFieldValue("")
                    onTextValueChanged("")
                }

                it.text.matches(validationPattern) -> {
                    tfValue = it
                    onTextValueChanged(it.text)
                }
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = if (allowDecimals) KeyboardType.Decimal else KeyboardType.NumberPassword, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
fun LettersOnlyEditText(
    valueString: String,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextValueChanged: (String) -> Unit
) {
    val validationPattern = remember { Regex("^([Α-Ωα-ωΆΈΉΊΌΎΏάέήίΐϊόύΰϋώ ]|[A-Za-z ])+\$") }
    var tfValue by remember(valueString) { mutableStateOf(TextFieldValue(text = valueString, selection = TextRange(valueString.length))) }
    TextField(
        modifier = modifier,
        value = tfValue,
        label = label,
        placeholder = placeholder,
        onValueChange = {
            when {
                it.text.isBlank() -> {
                    tfValue = TextFieldValue("")
                    onTextValueChanged("")
                }

                it.text.matches(validationPattern) -> {
                    tfValue = it
                    onTextValueChanged(it.text.trim())
                }
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

fun getUnitSuffixVisualTransformation(textLength: Int, unitSuffix: String): VisualTransformation {
    return VisualTransformation { text ->
        val annotatedString = AnnotatedString.Builder().run {
            append(text)
            if (text.text.isNotBlank()) {
                append(unitSuffix)
            }
            toAnnotatedString()
        }
        val offsetMapping = if (text.text.isNotBlank()) {
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int) = offset
                override fun transformedToOriginal(offset: Int) = offset.coerceIn(0, textLength)
            }
        } else OffsetMapping.Identity

        TransformedText(annotatedString, offsetMapping)
    }
}
