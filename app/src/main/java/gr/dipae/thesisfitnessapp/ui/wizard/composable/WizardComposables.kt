package gr.dipae.thesisfitnessapp.ui.wizard.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPagerIndicator
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessLLText
import gr.dipae.thesisfitnessapp.ui.base.compose.VerticalSpacerDefault
import gr.dipae.thesisfitnessapp.ui.theme.ColorDisabledButton
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme
import gr.dipae.thesisfitnessapp.ui.wizard.model.DietGoalUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.FitnessLevelUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardSportUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardStepGoalUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState
import gr.dipae.thesisfitnessapp.util.composable.DigitOnlyEditText
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

typealias OnFinishWizard = () -> Unit
typealias OnNextStep = () -> Unit

@ExperimentalFoundationApi
@Composable
fun WizardContent(
    uiState: WizardUiState,
    onNextStep: OnNextStep = {},
    onFinishWizard: OnFinishWizard = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingDefault_16dp, vertical = SpacingDouble_32dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState().apply {
            LaunchedEffect(key1 = settledPage) {
                uiState.wizardPageIndexState.value = settledPage
            }
        }
        LaunchedEffect(key1 = Unit) {
            snapshotFlow { uiState.isFinishButtonEnabled() }
                .collectLatest {
                    uiState.finishButtonEnabled.value = it
                }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.05f),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.3f),
                enabled = uiState.finishButtonEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorSecondary,
                    contentColor = Color.Black,
                    disabledContainerColor = ColorDisabledButton,
                    disabledContentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    if (uiState.wizardPageIndexState.value == uiState.wizardSteps - 1) {
                        onFinishWizard()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.settledPage + 1)
                        }
                    }
                }) {
                ThesisFitnessLLText(text = stringResource(uiState.getFinishButtonText()))
            }
        }
        VerticalSpacerDefault()
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f),
            state = pagerState,
            pageCount = uiState.wizardSteps
        ) { page ->
            uiState.WizardPageContent(page)
            onNextStep()
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(0.05f),
            pagerState = pagerState,
            pageCount = uiState.wizardSteps,
            activeColor = ColorSecondary,
            inactiveColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun WizardNameStep(usernameState: MutableState<String>) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        DigitOnlyEditText(
            valueString = usernameState.value,
            onTextValueChanged = { usernameState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_name_label))
            }
        )
    }
}

@Composable
fun WizardWeightStep(bodyWeightState: MutableState<String>) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        DigitOnlyEditText(
            valueString = bodyWeightState.value,
            onTextValueChanged = { bodyWeightState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_body_weight_label))
            }
        )
    }
}

@Composable
fun WizardFitnessLevelStep(fitnessLevels: List<FitnessLevelUiItem>, onSelectFitnessLevel: (FitnessLevelUiItem) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        fitnessLevels.forEach {
            ThesisFitnessHMAutoSizeText(
                modifier = Modifier.clickable { onSelectFitnessLevel(it) },
                text = it.fitnessLevel.name,
                color = it.textColor,
                maxFontSize = 26.sp
            )
        }
    }
}

@Composable
fun WizardStepGoal(stepGoalState: WizardStepGoalUiItem) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        DigitOnlyEditText(
            valueString = stepGoalState.goalAmount.value,
            onTextValueChanged = { stepGoalState.goalAmount.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_step_goal_label))
            }
        )
    }
}

@Composable
fun WizardFavoriteActivitiesStep(sports: List<WizardSportUiItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = sports, key = { item -> item.id }) { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f)
                    .clickable { item.selected.value = !item.selected.value },
                horizontalArrangement = Arrangement.spacedBy(SpacingDefault_16dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .aspectRatio(1f),
                    model = item.imageUrl.loadImageWithCrossfade(R.drawable.img_general_sport),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(item.color)
                )
                ThesisFitnessBLAutoSizeText(text = item.name, color = item.color, maxFontSize = 26.sp)
            }
        }
    }
}

@Composable
fun WizardDailyDietStep(dailyDietGoal: DietGoalUiItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        ThesisFitnessHLAutoSizeText(text = stringResource(id = R.string.wizard_daily_diet_step_title), color = MaterialTheme.colorScheme.primary, maxFontSize = 26.sp)

        val focusManager = LocalFocusManager.current
        DigitOnlyEditText(
            valueString = dailyDietGoal.caloriesState.value,
            onTextValueChanged = { dailyDietGoal.caloriesState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_calories_label))
            },
            focusManager = focusManager
        )

        DigitOnlyEditText(
            valueString = dailyDietGoal.proteinsState.value,
            onTextValueChanged = { dailyDietGoal.proteinsState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_proteins_label))
            },
            focusManager = focusManager
        )

        DigitOnlyEditText(
            valueString = dailyDietGoal.carbohydratesState.value,
            onTextValueChanged = { dailyDietGoal.carbohydratesState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_carbs_label))
            },
            focusManager = focusManager
        )

        DigitOnlyEditText(
            valueString = dailyDietGoal.fatsState.value,
            onTextValueChanged = { dailyDietGoal.fatsState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_fats_label))
            },
            focusManager = focusManager
        )

        DigitOnlyEditText(
            valueString = dailyDietGoal.waterMLState.value,
            onTextValueChanged = { dailyDietGoal.waterMLState.value = it },
            label = {
                ThesisFitnessLLText(text = stringResource(R.string.wizard_water_label))
            },
            focusManager = focusManager
        )
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun WizardContentPreview() {
    ThesisFitnessAppTheme {
        WizardContent(
            uiState = WizardUiState(
                wizardSteps = 5,
                fitnessLevels = FitnessLevel.values().map { FitnessLevelUiItem(fitnessLevel = it) },
                sports = listOf(WizardSportUiItem(id = "1", name = "Swimming", imageUrl = "", parameters = listOf())),
                wizardStepGoal = WizardStepGoalUiItem(),
                dailyDietGoal = DietGoalUiItem()
            )
        )
    }
}