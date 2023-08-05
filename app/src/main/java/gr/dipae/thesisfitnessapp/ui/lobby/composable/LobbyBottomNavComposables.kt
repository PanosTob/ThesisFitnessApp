package gr.dipae.thesisfitnessapp.ui.lobby.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.lobby.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.theme.ColorBottomNavBar
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp

@Composable
fun RowScope.LobbyBottomNavItem(
    item: BottomAppBarUiItem,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .weight(0.2f)
            .fillMaxHeight(),
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = item.iconColor(),
            containerColor = ColorBottomNavBar
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
                color = item.iconColor()
            )
        }
    }
}