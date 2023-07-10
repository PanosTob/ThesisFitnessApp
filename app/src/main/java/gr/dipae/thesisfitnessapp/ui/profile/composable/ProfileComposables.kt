package gr.dipae.thesisfitnessapp.ui.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@Composable
fun ProfileContent(
    onLogout: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        ThesisFitnessHLText(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogout() }
                .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
            text = stringResource(id = R.string.profile_logout_btn),
            color = Color.White,
            fontSize = 26.sp
        )
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ThesisFitnessAppTheme {
        ProfileContent()
    }
}