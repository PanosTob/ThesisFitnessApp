package gr.dipae.thesisfitnessapp.ui.lobby.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.app.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalMaterial3Api
@Composable
fun LobbyContent(
    uiState: LobbyUiState
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f), horizontalArrangement = Arrangement.spacedBy(SpacingDefault_16dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(), painter = painterResource(id = R.drawable.img_generic_face), contentDescription = ""
            )
            Column(modifier = Modifier.weight(0.6f), verticalArrangement = Arrangement.spacedBy(SpacingHalf_8dp), horizontalAlignment = Alignment.CenterHorizontally) {
                ThesisFitnessHLAutoSizeText(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(), text = uiState.userDetails.username, maxFontSize = 38.sp, color = Color.White, textAlign = TextAlign.Center
                )
                Row(modifier = Modifier.weight(0.5f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                    LobbyUserDetailItem("64kg", R.drawable.ic_scale)
                    LobbyUserDetailItem("35%", R.drawable.ic_muscle)
                    LobbyUserDetailItem("12%", R.drawable.ic_weight)
                }
            }
        }

    }
}

@Composable
fun RowScope.LobbyBottomNabItem(
    item: BottomAppBarUiItem,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .weight(0.2f)
            .fillMaxHeight(),
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = item.getColor(),
            containerColor = Color.Black
        ),
        onClick = { onClick() }) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .padding(SpacingHalf_8dp),
                painter = painterResource(id = item.iconRes),
                contentDescription = ""
            )
            ThesisFitnessBMText(
                modifier = Modifier
                    .weight(0.3f),
                text = stringResource(id = item.labelStringRes),
                color = item.getColor()
            )
        }
    }
}

@Composable
fun RowScope.LobbyUserDetailItem(
    text: String,
    iconRes: Int
) {
    Column(modifier = Modifier.weight(0.33f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(SpacingQuarter_4dp)) {
        ThesisFitnessBLAutoSizeText(modifier = Modifier.fillMaxWidth(), text = text, maxFontSize = 24.sp, color = Color.White, textAlign = TextAlign.Center)
        Icon(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(1f), painter = painterResource(id = iconRes), contentDescription = "", tint = ColorSecondary
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun LobbyContentPreview() {
    ThesisFitnessAppTheme {
        LobbyContent(
            LobbyUiState(
                userDetails = UserDetailsUiItem(username = "Panagiotis Toumpas")
            )
        )
    }
}