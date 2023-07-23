package gr.dipae.thesisfitnessapp.ui.app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import javax.inject.Inject
import javax.inject.Singleton

data class AppUiState(
    val topBarState: TopBarUiState = TopBarUiState(),
    val bottomAppBarItems: MutableState<List<BottomAppBarUiItem>> = mutableStateOf(emptyList()),
    val colorScreen: MutableState<Color> = mutableStateOf(Color.Black),
    val navigateToWizard: MutableState<Boolean> = mutableStateOf(false),
    val navigateToLobby: MutableState<Boolean> = mutableStateOf(false),
    val navigateBackToLogin: MutableState<Boolean> = mutableStateOf(false)
) {
    fun onBottomAppBarItemSelection(item: BottomAppBarUiItem) {
        bottomAppBarItems.value.onEach { it.selected.value = false }
        bottomAppBarItems.value.find { it == item }?.selected?.value = true
    }
}

data class TopBarUiState(
    val titleRes: MutableState<Int> = mutableStateOf(R.string.empty),
    val navigationIcon: MutableState<ImageVector?> = mutableStateOf(null),
    val actionIcons: MutableState<List<TopBarActionUiItem>> = mutableStateOf(listOf(TopBarActionUiItem(R.drawable.ic_top_burger_menu))),
    val isVisible: MutableState<Boolean> = mutableStateOf(false)
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
    fun getColor(): Color = if (selected.value) ColorSecondary else Color.White
}

@Singleton
class PoDHelper @Inject constructor() {
    var isAlreadyCreated = false

    fun initPoDHelper() {
        isAlreadyCreated = true
    }
}