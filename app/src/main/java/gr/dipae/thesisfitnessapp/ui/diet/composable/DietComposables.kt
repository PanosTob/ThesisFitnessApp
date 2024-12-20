package gr.dipae.thesisfitnessapp.ui.diet.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessHMAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.base.compose.ifable
import gr.dipae.thesisfitnessapp.ui.diet.model.DietLobbyUiState
import gr.dipae.thesisfitnessapp.ui.diet.model.NutritionProgressBarUiItem
import gr.dipae.thesisfitnessapp.ui.diet.model.NutritionProgressBarsUiItem
import gr.dipae.thesisfitnessapp.ui.diet.navigation.OnFoodSelectionFabClicked
import gr.dipae.thesisfitnessapp.ui.diet.navigation.OnMacrosFabClicked
import gr.dipae.thesisfitnessapp.ui.theme.ColorJumpingRope
import gr.dipae.thesisfitnessapp.ui.theme.ColorPingPong
import gr.dipae.thesisfitnessapp.ui.theme.ColorRunning
import gr.dipae.thesisfitnessapp.ui.theme.ColorSwimming
import gr.dipae.thesisfitnessapp.ui.theme.ColorWalking
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDouble_32dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp
import gr.dipae.thesisfitnessapp.util.composable.ArcProgressBar


@Composable
fun DietContent(
    uiState: DietLobbyUiState?,
    onFoodSelectionFabClicked: OnFoodSelectionFabClicked,
    onMacrosFabClicked: OnMacrosFabClicked
) {
    uiState?.let {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = SpacingCustom_24dp)
        ) {
            uiState.pastDate?.let {
                ThesisFitnessHLAutoSizeText(
                    text = stringResource(id = R.string.diet_past_date_title, it),
                    maxLines = 1,
                    maxFontSize = 26.sp
                )
            }
            NutritionProgressBars(
                modifier = Modifier
                    .fillMaxWidth()
                    .ifable(uiState.pastDate != null) { padding(top = SpacingDouble_32dp) },
                nutritionProgressBars = uiState.nutritionProgressBars
            )
            DietLobbyFab(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .align(Alignment.BottomEnd),
                onFoodSelectionFabClicked = { onFoodSelectionFabClicked() },
                onMacrosFabClicked = { onMacrosFabClicked() }
            )
        }
    }
}

@Composable
fun NutritionProgressBars(
    modifier: Modifier,
    nutritionProgressBars: NutritionProgressBarsUiItem
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        NutritionProgressBarRow(
            nutritionProgressBars.caloriesBar,
            R.string.diet_nutrition_progress_bar_cal,
            color = ColorJumpingRope
        )

        WidthAdjustedDivider(Modifier.fillMaxWidth(1f), color = MaterialTheme.colorScheme.primary)
        NutritionProgressBarRow(
            nutritionProgressBars.proteinBar,
            R.string.diet_nutrition_progress_bar_protein,
            color = ColorWalking
        )

        WidthAdjustedDivider(Modifier.fillMaxWidth(1f), color = MaterialTheme.colorScheme.primary)
        NutritionProgressBarRow(
            nutritionProgressBars.carbsBar,
            R.string.diet_nutrition_progress_bar_carb,
            color = ColorRunning
        )

        WidthAdjustedDivider(Modifier.fillMaxWidth(1f), color = MaterialTheme.colorScheme.primary)
        NutritionProgressBarRow(
            nutritionProgressBars.fatsBar,
            R.string.diet_nutrition_progress_bar_fats,
            color = ColorPingPong
        )

        WidthAdjustedDivider(Modifier.fillMaxWidth(1f), color = MaterialTheme.colorScheme.primary)
        NutritionProgressBarRow(
            nutritionProgressBars.waterBar,
            R.string.diet_nutrition_progress_bar_water,
            color = ColorSwimming
        )
    }
}

@Composable
fun NutritionProgressBarRow(
    progressBarItem: NutritionProgressBarUiItem,
    titleRes: Int,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f)
    ) {
        ArcProgressBarWithTextUnderneath(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            progress = progressBarItem.progressTowardsGoal,
            amount = progressBarItem.amount,
            color = color
        )
        Column(
            Modifier
                .weight(0.6f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            ThesisFitnessHMAutoSizeText(text = stringResource(id = titleRes), color = color)
        }
    }
}

@Composable
fun ArcProgressBarWithTextUnderneath(
    modifier: Modifier,
    progress: Float,
    amount: String,
    color: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        ArcProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            color = color,
            progress = progress,
            strokeWidth = SpacingDefault_16dp
        )
        ThesisFitnessHMAutoSizeText(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = SpacingQuarter_4dp),
            text = amount,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = color
        )
    }
}

@Composable
fun DietLobbyFab(
    modifier: Modifier,
    onFoodSelectionFabClicked: OnFoodSelectionFabClicked,
    onMacrosFabClicked: OnMacrosFabClicked
) {
    var fabExpandedState by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {

        AnimatedVisibility(
            visible = fabExpandedState,
            enter = fadeIn(animationSpec = tween(delayMillis = 150, durationMillis = 500, easing = LinearOutSlowInEasing))
                    + slideIn(
                initialOffset = { IntOffset((it.width / 2) - ((it.width * 0.47) / 1).toInt(), it.height) },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ),
            exit = fadeOut(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing))
                    + slideOut(
                targetOffset = { IntOffset((it.width / 2) - ((it.width * 0.47) / 1).toInt(), it.height) },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .graphicsLayer {
                            clip = true
                            shape = CircleShape
                        }
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            fabExpandedState = false
                            onFoodSelectionFabClicked()
                        }
                        .padding(SpacingQuarter_4dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.ic_food_list),
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = ""
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .graphicsLayer {
                            clip = true
                            shape = CircleShape
                        }
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            fabExpandedState = false
                            onMacrosFabClicked()
                        }
                        .padding(SpacingQuarter_4dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.ic_macros),
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = ""
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                }
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    fabExpandedState = !fabExpandedState
                }
                .padding(SpacingCustom_24dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}