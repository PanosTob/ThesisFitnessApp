package gr.dipae.thesisfitnessapp.ui.diet.foodselection.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLText
import gr.dipae.thesisfitnessapp.ui.base.compose.WidthAdjustedDivider
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.SearchBarActionType
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.util.composable.isScrolledToEnd
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter

private typealias OnFoodItemClicked = (FoodUiItem) -> Unit

@ExperimentalFoundationApi
@Composable
fun FoodSelectionContent(
    uiState: FoodSelectionUiState,
    onFoodItemClicked: OnFoodItemClicked,
    onSearchFood: (String) -> Unit,
    onClearSearch: () -> Unit,
    onPageSizeReached: () -> Unit
) {
    val lazyListScrollState = rememberLazyListState()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lazyListScrollState, lifecycleOwner) {
        snapshotFlow { lazyListScrollState.isScrolledToEnd() }
            .filter { it }
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                onPageSizeReached()
            }
    }
    val localFocusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = SpacingCustom_24dp, vertical = SpacingDefault_16dp),
        state = lazyListScrollState,
        verticalArrangement = Arrangement.spacedBy(SpacingHalf_8dp)
    ) {
        stickyHeader {
            FoodSelectionSearchBar(
                searchBarTrailingActionType = uiState.searchBarTrailingActionType.value,
                onClearSearch = { onClearSearch() },
                onSearch = {
                    localFocusManager.clearFocus()
                    onSearchFood(it)
                }
            )
        }
        items(items = uiState.foodList, key = { item -> item.id }) { item ->
            FoodSelectionItem(item) { onFoodItemClicked(it) }
            WidthAdjustedDivider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun FoodSelectionSearchBar(
    searchBarTrailingActionType: SearchBarActionType,
    onSearch: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    var foodNameValueText by remember { mutableStateOf(TextFieldValue(text = "", selection = TextRange(0, Int.MAX_VALUE))) }
    val validationPattern = remember { Regex("^[^<>%\$\\d]*\$") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6f),
        value = foodNameValueText,
        placeholder = {
            ThesisFitnessBLAutoSizeText(text = stringResource(id = R.string.diet_food_list_search_bar_placeholder), maxLines = 1)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(0.1f)
                    .aspectRatio(1f)
                    .clickable {
                        if (searchBarTrailingActionType is SearchBarActionType.Search) {
                            onSearch(foodNameValueText.text)
                        } else {
                            foodNameValueText = TextFieldValue()
                            onClearSearch()
                        }
                    },
                painter = painterResource(id = if (searchBarTrailingActionType is SearchBarActionType.Search) R.drawable.ic_search else R.drawable.ic_clear_search),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        onValueChange = {
            when {
                it.text.isBlank() -> {
                    if (foodNameValueText.text.isNotBlank()) {
                        onClearSearch()
                    }
                    foodNameValueText = TextFieldValue("")
                }

                it.text.matches(validationPattern) -> {
                    foodNameValueText = it
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(foodNameValueText.text)
        })
    )
}

@Composable
fun FoodSelectionItem(
    item: FoodUiItem,
    onFoodItemClicked: OnFoodItemClicked
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7f)
            .clickable { onFoodItemClicked(item) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThesisFitnessBLText(
            text = item.name,
            fontSize = 22.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}