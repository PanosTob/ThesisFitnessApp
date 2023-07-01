package gr.dipae.thesisfitnessapp.ui.welcome.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessLLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDouble
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerHalf
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_12dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingEighth_2dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme
import gr.dipae.thesisfitnessapp.ui.welcome.model.LoginUiState
import gr.dipae.thesisfitnessapp.util.ext.pxToDp

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onGoogleSignInClicked: (String) -> Unit = {}
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    var contentHeightDp by remember { mutableStateOf(IntSize.Zero) }

    val contentOffsetY = remember(contentHeightDp) {
        ((screenHeightDp * 0.5) - contentHeightDp.height.pxToDp).dp
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(horizontal = SpacingDefault_16dp, vertical = SpacingDouble_32dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .onSizeChanged {
                    contentHeightDp = it
                }
                .offset(y = contentOffsetY)
                .fillMaxWidth()
                .padding(top = SpacingDouble_32dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = ""
            )
            VerticalSpacerDefault()
            VerticalSpacerHalf()
            ThesisFitnessHLAutoSizeText(
                text = stringResource(id = R.string.welcome_screen_title),
                maxLines = 1,
                color = Color.White,
                maxFontSize = 28.sp
            )

            VerticalSpacerDouble()

            val googleWebClientId = stringResource(id = R.string.default_web_client_id)
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(BorderStroke(SpacingEighth_2dp, ColorSecondary), RoundedCornerShape(SpacingDefault_16dp))
                    .clickable { onGoogleSignInClicked(googleWebClientId) }
                    .padding(SpacingCustom_12dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.ic_logo_google),
                    contentDescription = ""
                )
                ThesisFitnessLLAutoSizeText(
                    text = stringResource(id = R.string.welcome_screen_sign_in_btn),
                    color = Color.White,
                    maxLines = 1,
                    maxFontSize = 22.sp
                )
            }
        }
        ThesisFitnessLLAutoSizeText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.welcome_screen_guest_btn),
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            maxFontSize = 22.sp
        )
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    ThesisFitnessAppTheme {
        LoginContent(LoginUiState())
    }
}