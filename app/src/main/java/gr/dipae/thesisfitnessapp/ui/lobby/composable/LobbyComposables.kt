package gr.dipae.thesisfitnessapp.ui.lobby.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.lobby.model.BottomAppBarUiItem
import gr.dipae.thesisfitnessapp.ui.lobby.model.LobbyUiState
import gr.dipae.thesisfitnessapp.ui.lobby.viewmodel.LobbyViewModel
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalMaterial3Api
@Composable
fun LobbyContent(
    uiState: LobbyUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary,
                    navigationIconContentColor = ColorSecondary
                ),
                title = {
                    ThesisFitnessBMText(text = stringResource(id = R.string.lobby_top_bar_title), color = Color.White, fontSize = 22.sp)
                },
                actions = {
                    Icon(modifier = Modifier.padding(end = SpacingDefault_16dp), painter = painterResource(id = R.drawable.ic_top_burger_menu), contentDescription = "", tint = ColorSecondary)
                }
            )
        },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = ColorPrimary)
                    .padding(top = it.calculateTopPadding())
            ) {

            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black
            ) {
                uiState.bottomAppBarItems.forEach {
                    LobbyBottomNabItem(item = it) {
                        uiState.onBottomAppBarItemSelection(it)
                    }
                }
            }
        }
    )
}

@Composable
fun RowScope.LobbyBottomNabItem(
    item: BottomAppBarUiItem,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .weight(0.2f)
            .fillMaxHeight(),
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = item.getColor(),
            containerColor = Color.Black
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
                color = item.getColor()
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun LobbyContentPreview() {
    ThesisFitnessAppTheme {
        LobbyContent(LobbyUiState(bottomAppBarItems = LobbyViewModel.bottomAppBarUiItems))
    }
}