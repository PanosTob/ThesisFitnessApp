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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.diet.navigation.OnFoodSelectionFabClicked
import gr.dipae.thesisfitnessapp.ui.diet.navigation.OnMacrosFabClicked
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingQuarter_4dp

@Composable
fun DietContent(
    onFoodSelectionFabClicked: OnFoodSelectionFabClicked,
    onMacrosFabClicked: OnMacrosFabClicked
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp)
    ) {
        var fabExpandedState by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .align(Alignment.BottomEnd),
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
}