package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import gr.dipae.thesisfitnessapp.ui.base.compose.ifable
import gr.dipae.thesisfitnessapp.ui.sport.session.model.PlayStateButtonUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorDisabledButton
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@Composable
fun SportSessionPlayButton(
    modifier: Modifier,
    buttonItem: PlayStateButtonUiItem,
    onClick: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .graphicsLayer {
                clip = true
                shape = CircleShape
            }
            .drawBehind {
                val backgroundColor = if (buttonItem.isEnabled.value) primaryColor else ColorDisabledButton
                drawCircle(backgroundColor)
            }
            .ifable(buttonItem.isEnabled.value) {
                clickable {
                    onClick()
                }
            }
            .padding(SpacingHalf_8dp)
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(buttonItem.iconRes.value),
            tint = MaterialTheme.colorScheme.background,
            contentDescription = ""
        )
    }
}