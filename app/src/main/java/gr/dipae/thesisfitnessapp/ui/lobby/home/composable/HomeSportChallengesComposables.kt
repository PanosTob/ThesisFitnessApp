package gr.dipae.thesisfitnessapp.ui.lobby.home.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.HorizontalSpacerHalf
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerHalf
import gr.dipae.thesisfitnessapp.ui.diet.composable.ArcProgressBar
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.UserSportChallengeUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorDividerGrey
import gr.dipae.thesisfitnessapp.ui.theme.ColorGold
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.openSansFontFamily
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade

@Composable
fun HomeSportChallenges(
    sportChallenges: List<UserSportChallengeUiItem>
) {
    ThesisFitnessHLAutoSizeText(text = stringResource(id = R.string.home_user_challenges_section), maxFontSize = 21.sp, maxLines = 1, style = TextStyle(fontFamily = openSansFontFamily))
    VerticalSpacerHalf()
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)) {
        sportChallenges.forEach {
            HomeSportChallenge(it)
        }
    }
}

@Composable
fun HomeSportChallenge(
    item: UserSportChallengeUiItem
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f)
            .border(BorderStroke(2.dp, ColorDividerGrey), RoundedCornerShape(SpacingHalf_8dp))
            .clip(RoundedCornerShape(SpacingHalf_8dp))
            .padding(SpacingDefault_16dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            ArcProgressBar(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                progress = item.progress,
                amount = item.goalValue,
                color = Color.White
            )
            Column(
                Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ThesisFitnessHMAutoSizeText(text = item.goalName, color = Color.White)
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            ThesisFitnessHMAutoSizeText(
                text = item.sportName,
                color = Color.White
            )
            HorizontalSpacerHalf()
            AsyncImage(modifier = Modifier.fillMaxWidth(0.1f), model = item.sportImgUrl.loadImageWithCrossfade(), contentDescription = "", colorFilter = ColorFilter.tint(Color.White))
        }
        if (item.completed) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(0.07f)
                    .aspectRatio(1f)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_trophy),
                contentDescription = null,
                tint = ColorGold,
            )
        }
    }
}