package gr.dipae.thesisfitnessapp.ui.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLText
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
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
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = SpacingCustom_24dp, horizontal = SpacingCustom_24dp),
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)) {
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_change_body_btn),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
            WidthAdjustedDivider(Modifier.fillMaxWidth())
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_change_daily_movement_goals_btn),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
            WidthAdjustedDivider(Modifier.fillMaxWidth())
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_change_daily_diet_goals_btn),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
            WidthAdjustedDivider(Modifier.fillMaxWidth())
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_change_language_btn),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_logout_btn),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.sp
            )
            ThesisFitnessHLText(
                modifier = Modifier
                    .clickable { onLogout() }
                    .padding(vertical = SpacingHalf_8dp, horizontal = SpacingDefault_16dp),
                text = stringResource(id = R.string.profile_delete_acc_btn),
                color = Color.Red,
                fontSize = 22.sp
            )
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ThesisFitnessAppTheme {
        ProfileContent()
    }
}