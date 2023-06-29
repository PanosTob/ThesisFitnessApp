package gr.dipae.thesisfitnessapp.ui.base.compose

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun ThesisFitnessHLText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineLarge,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessHLAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineLarge,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessHMText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.headlineMedium.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineMedium,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun ThesisFitnessHMAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.headlineMedium.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineMedium,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessHSText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineSmall,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessHSAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.headlineSmall,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessBLText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.headlineLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.bodyLarge,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessBLAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.bodyLarge,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessBMText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.bodyMedium,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessBMAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.bodyMedium,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessLLText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.labelLarge,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = fontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}

@Composable
fun ThesisFitnessLLAutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    maxFontSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    lineHeightMultiplier: Float = 1f,
    style: TextStyle? = null
) {
    AutoSizeText(
        text = text,
        modifier = modifier,
        style = style ?: MaterialTheme.typography.labelLarge,
        color = color,
        maxFontSize = maxFontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = maxFontSize * lineHeightMultiplier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines
    )
}


@Composable
fun AutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    maxFontSize: TextUnit,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current
) {
    var scaledFontSize by remember { mutableStateOf(maxFontSize) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        modifier = modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        text = text,
        color = color,
        fontSize = scaledFontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style,
        onTextLayout = { textLayoutResult ->
            when {
                textLayoutResult.didOverflowHeight || textLayoutResult.didOverflowWidth -> scaledFontSize *= 0.9
                else -> readyToDraw = true
            }
        }
    )
}