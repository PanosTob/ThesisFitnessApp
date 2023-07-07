package gr.dipae.thesisfitnessapp.ui.lobby.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBMText
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.ThesisFitnessAppTheme

@ExperimentalMaterial3Api
@Composable
fun LobbyContent() {
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
                containerColor = ColorPrimary
            ) {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(imageVector = Icons.Default.Home, "") },
                        label = { Text(text = "Home") },
                        selectedContentColor = ColorSecondary,
                        unselectedContentColor = Color.White,
                        selected = (true),
                        onClick = {})

                    BottomNavigationItem(
                        icon = { Icon(imageVector = Icons.Default.Favorite, "") },
                        label = { Text(text = "Favorite") },
                        selectedContentColor = ColorSecondary,
                        unselectedContentColor = Color.White,
                        selected = (false),
                        onClick = {})

                    BottomNavigationItem(
                        icon = { Icon(imageVector = Icons.Default.Person, "") },
                        label = { Text(text = "Profile") },
                        selectedContentColor = ColorSecondary,
                        unselectedContentColor = Color.White,
                        selected = (false),
                        onClick = {})
                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun LobbyContentPreview() {
    ThesisFitnessAppTheme {
        LobbyContent()
    }
}