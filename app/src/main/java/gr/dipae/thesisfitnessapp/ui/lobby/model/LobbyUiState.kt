package gr.dipae.thesisfitnessapp.ui.lobby.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.lobby.viewModel.LobbyViewModel

data class LobbyUiState(
    val topBarState: TopBarUiState = TopBarUiState(),
    val bottomAppBarItems: MutableState<List<BottomAppBarUiItem>> = mutableStateOf(LobbyViewModel.bottomAppBarUiItems),
) {
    fun onBottomAppBarItemSelection(item: BottomAppBarUiItem) {
        bottomAppBarItems.value.onEach { it.selected.value = false }
        bottomAppBarItems.value.find { it == item }?.selected?.value = true
    }
}

data class TopBarUiState(
    val titleRes: MutableState<Int> = mutableStateOf(R.string.empty),
    val navigationIcon: MutableState<ImageVector?> = mutableStateOf(null),
    val actionIcons: MutableState<List<TopBarActionUiItem>> = mutableStateOf(listOf(TopBarActionUiItem(R.drawable.ic_edit))),
    val isVisible: MutableState<Boolean> = mutableStateOf(true)
)

data class TopBarActionUiItem(
    val icon: Int,
    val clickAction: () -> Unit = {}
)

data class BottomAppBarUiItem(
    @StringRes val labelStringRes: Int,
    @DrawableRes val iconRes: Int,
    val route: String,
    val selected: MutableState<Boolean> = mutableStateOf(false),
) {
    val iconColor: @Composable () -> Color = { if (selected.value) MaterialTheme.colorScheme.primary else Color.White }
}