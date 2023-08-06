package gr.dipae.thesisfitnessapp.ui.diet.foodselection.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLText
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp

private typealias OnFoodSelectionItemClicked = (FoodUiItem) -> Unit

@Composable
fun FoodSelectionContent(
    uiState: FoodSelectionUiState,
    onFoodSelectionItemClicked: OnFoodSelectionItemClicked
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),

        ) {
        items(items = uiState.foodList, key = { item -> item.id }) { item ->
            FoodSelectionItem(item) { }
        }
    }
}

@Composable
fun FoodSelectionItem(
    item: FoodUiItem,
    onFoodSelectionItemClicked: OnFoodSelectionItemClicked
) {
    Row(
        Modifier
            .fillMaxWidth()
            .aspectRatio(5f)
            .clickable {
                onFoodSelectionItemClicked(item)
            }
    ) {
        ThesisFitnessBLText(text = item.name, fontSize = 24.sp)
    }
}