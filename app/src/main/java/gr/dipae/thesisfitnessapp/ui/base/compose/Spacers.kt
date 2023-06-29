package gr.dipae.thesisfitnessapp.ui.base.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_12dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingEighth_2dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingTriple_48dp

@Composable
fun VerticalSpacerDouble() {
    Spacer(modifier = Modifier.height(SpacingDouble_32dp))
}

@Composable
fun VerticalSpacerDefault() {
    Spacer(modifier = Modifier.height(SpacingDefault_16dp))
}

@Composable
fun VerticalSpacerHalfDouble() {
    Spacer(modifier = Modifier.height(SpacingCustom_12dp))
}

@Composable
fun VerticalSpacerHalf() {
    Spacer(modifier = Modifier.height(SpacingHalf_8dp))
}

@Composable
fun VerticalSpacerQuarter() {
    Spacer(modifier = Modifier.height(SpacingQuarter_4dp))
}

@Composable
fun VerticalSpacerEighth() {
    Spacer(modifier = Modifier.height(SpacingEighth_2dp))
}

@Composable
fun HorizontalSpacerTriple() {
    Spacer(modifier = Modifier.width(SpacingTriple_48dp))
}

@Composable
fun HorizontalSpacerDefault() {
    Spacer(modifier = Modifier.width(SpacingDefault_16dp))
}

@Composable
fun HorizontalSpacerHalf() {
    Spacer(modifier = Modifier.width(SpacingHalf_8dp))
}

@Composable
fun HorizontalSpacerQuarter() {
    Spacer(modifier = Modifier.width(SpacingQuarter_4dp))
}

@Composable
fun HorizontalSpacerEighth() {
    Spacer(modifier = Modifier.width(SpacingEighth_2dp))
}

@Composable
fun VerticalSpacer(spacing: Dp) {
    Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun HorizontalSpacer(spacing: Dp) {
    Spacer(modifier = Modifier.width(spacing))
}

