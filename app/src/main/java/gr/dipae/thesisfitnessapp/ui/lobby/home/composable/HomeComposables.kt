package gr.dipae.thesisfitnessapp.ui.lobby.home.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDouble
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserDetailsUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserMovementTrackingUiItems
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingEighth_2dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade

@ExperimentalMaterial3Api
@Composable
fun HomeContent(
    uiState: HomeUiState
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        HomeUserDetails(uiState.userDetails)
        VerticalSpacerDouble()
        HomeDailyMovementStats(uiState.userMovementTracking)

        VerticalSpacerDefault()
        WidthAdjustedDivider(Modifier.fillMaxWidth())
        VerticalSpacerDefault()

        HomeSportChallenges(uiState.sportChallenges)
    }
}

@Composable
fun HomeUserDetails(
    userDetails: UserDetailsUiItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.5f), horizontalArrangement = Arrangement.spacedBy(SpacingDefault_16dp), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(0.4f)
                .aspectRatio(1f)
                .border(BorderStroke(width = SpacingEighth_2dp, color = MaterialTheme.colorScheme.primary), shape = CircleShape)
                .clip(shape = CircleShape),
            model = userDetails.imageUrl.loadImageWithCrossfade(),
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(0.6f), verticalArrangement = Arrangement.spacedBy(SpacingHalf_8dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ThesisFitnessHLAutoSizeText(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth(), text = userDetails.username, maxFontSize = 38.sp, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.weight(0.5f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                HomeUserDetailItem(userDetails.bodyWeight, R.drawable.ic_scale)
                HomeUserDetailItem(userDetails.muscleMassPercent, R.drawable.ic_muscle)
                HomeUserDetailItem(userDetails.bodyFatPercent, R.drawable.ic_weight)
            }
        }
    }
}

@Composable
fun HomeDailyMovementStats(
    userMovementTracking: UserMovementTrackingUiItems
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeUserTrackingStat(
            title = stringResource(id = R.string.wizard_calories_label),
            trackingItem = userMovementTracking.caloriesTracking,
            imgRes = R.drawable.ic_caloric_burn
        )
        VerticalSpacerDefault()
        HomeUserTrackingStat(
            title = stringResource(id = R.string.home_step_counter_label),
            trackingItem = userMovementTracking.stepsTracking,
            imgRes = R.drawable.ic_steps_track
        )
    }
}

@Composable
fun HomeUserTrackingStat(
    title: String,
    trackingItem: UserMovementTrackingUiItem,
    imgRes: Int
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val progressAnimation by animateFloatAsState(
        targetValue = trackingItem.fulfillmentPercentage.value,
        animationSpec = tween(3000, 500)
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingHalf_8dp)
    ) {
        ThesisFitnessBLAutoSizeText(text = title, maxLines = 1)
        Image(modifier = Modifier
            .graphicsLayer { clip = true }
            .drawBehind {
                val path = Path().apply {
                    moveTo(0f, size.height)
                    addRect(Rect(topLeft = Offset.Zero, bottomRight = Offset(size.width, size.height * progressAnimation)))
                }

                drawPath(path = path, color = primaryColor)
            }, painter = painterResource(id = imgRes), contentDescription = ""
        )
        ThesisFitnessBLAutoSizeText(text = trackingItem.remaining.value, maxLines = 1)
        ThesisFitnessBLAutoSizeText(text = stringResource(id = R.string.home_remaining_goal), maxLines = 1)
    }
}

@Composable
fun RowScope.HomeUserDetailItem(
    text: String,
    iconRes: Int
) {
    Column(modifier = Modifier.weight(0.33f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(SpacingQuarter_4dp)) {
        ThesisFitnessBLAutoSizeText(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            maxFontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
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
                userDetails = UserDetailsUiItem(
                    username = "Panagiotis Toumpas",
                    imageUrl = "",
                    bodyWeight = "64kg",
                    dailyStepGoal = 10000,
                    dailyCaloricBurnGoal = 1500,
                    muscleMassPercent = "0.25%",
                    bodyFatPercent = "0.17%"
                ),
                sportChallenges = emptyList(),
                userMovementTracking = UserMovementTrackingUiItems(
                    stepsTracking = UserMovementTrackingUiItem(remaining = remember { mutableStateOf("10000") }),
                    caloriesTracking = UserMovementTrackingUiItem(
                        remaining = remember { mutableStateOf("1500") },
                    ),
                )
            )
        )
    }
}