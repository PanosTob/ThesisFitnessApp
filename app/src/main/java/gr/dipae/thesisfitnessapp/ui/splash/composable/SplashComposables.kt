package gr.dipae.thesisfitnessapp.ui.splash.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.splash.model.SplashUiState
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@Composable
fun SplashContent(
    uiState: SplashUiState
) {
    Surface(color = Color.Black) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            painter = painterResource(id = uiState.img),
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashContentPreview() {
    ThesisFitnessAppTheme {
        SplashContent(
            SplashUiState(R.drawable.app_logo)
        )
    }
}