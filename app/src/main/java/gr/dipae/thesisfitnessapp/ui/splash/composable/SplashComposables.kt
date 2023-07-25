package gr.dipae.thesisfitnessapp.ui.splash.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@Composable
fun SplashContent(
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashContentPreview() {
    ThesisFitnessAppTheme {
        SplashContent()
    }
}