package gr.dipae.thesisfitnessapp.ui.lobby.home.composable

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalMaterial3Api
@Composable
fun HomeContent(
    uiState: HomeUiState
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
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
                        .fillMaxWidth(), text = uiState.userDetails.username, maxFontSize = 38.sp, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center
                )
                Row(modifier = Modifier.weight(0.5f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                    HomeUserDetailItem("64kg", R.drawable.ic_scale)
                    HomeUserDetailItem("35%", R.drawable.ic_muscle)
                    HomeUserDetailItem("12%", R.drawable.ic_weight)
                }
            }
        }

    }
}

@Composable
fun RowScope.HomeUserDetailItem(
    text: String,
    iconRes: Int
) {
    Column(modifier = Modifier.weight(0.33f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(SpacingQuarter_4dp)) {
        ThesisFitnessBLAutoSizeText(modifier = Modifier.fillMaxWidth(), text = text, maxFontSize = 24.sp, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        Icon(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(1f),
            painter = painterResource(id = iconRes),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun HomeContentPreview() {
    ThesisFitnessAppTheme {
        HomeContent(
            HomeUiState(
                userDetails = UserDetailsUiItem(username = "Panagiotis Toumpas")
            )
        )
    }
}