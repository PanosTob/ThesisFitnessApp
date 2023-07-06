package gr.dipae.thesisfitnessapp.ui.wizard.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.HorizontalPagerIndicator
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessLLText
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.wizard.model.FitnessLevelUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState

@ExperimentalFoundationApi
@Composable
fun WizardContent(
    uiState: WizardUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingDefault_16dp, vertical = SpacingDouble_32dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            state = pagerState,
            pageCount = 4
        ) { page ->
            uiState.WizardPageContent(page)
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            pagerState = pagerState,
            pageCount = 4,
            activeColor = ColorSecondary,
            inactiveColor = Color.White
        )
    }
}

@Composable
fun WizardNameStep(usernameState: MutableState<String>) {
    TextField(
        value = usernameState.value,
        onValueChange = { usernameState.value = it },
        label = {
            ThesisFitnessLLText(text = stringResource(R.string.wizard_name_label))
        }
    )
}

@Composable
fun WizardFitnessLevelStep(fitnessLevels: List<FitnessLevelUiItem>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        fitnessLevels.forEach {
            ThesisFitnessBLAutoSizeText(text = it.fitnessLevel.name, color = it.textColor)
        }
    }
}

@Composable
fun WizardFavoriteActivitiesStep() {

}

@Composable
fun WizardDailyDietStep() {

}

@Preview
@Composable
fun WizardContentPreview() {

}