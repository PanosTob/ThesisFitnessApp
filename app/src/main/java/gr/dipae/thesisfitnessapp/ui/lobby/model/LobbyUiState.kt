package gr.dipae.thesisfitnessapp.ui.lobby.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary

data class LobbyUiState(
    val selectedBottomNavItemIndex: MutableState<Int> = mutableStateOf(0),
    val username: String,
    val bottomAppBarItems: List<BottomAppBarUiItem>
) {
    fun onBottomAppBarItemSelection(item: BottomAppBarUiItem) {
        bottomAppBarItems.onEach { it.selected.value = false }
        bottomAppBarItems.find { it == item }?.selected?.value = true
    }
}

data class BottomAppBarUiItem(
    @StringRes val labelStringRes: Int,
    @DrawableRes val iconRes: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
) {
    fun getColor(): Color = if (selected.value) ColorSecondary else Color.White
}