package gr.dipae.thesisfitnessapp.ui.sport.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.sport.model.SportUiItem
import gr.dipae.thesisfitnessapp.ui.sport.navigation.OnSportSelected
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingHalf_8dp
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade

@Composable
fun SportItem(
    item: SportUiItem,
    onSportSelected: OnSportSelected
) {
    val background = MaterialTheme.colorScheme.primary
    Box(Modifier
        .fillMaxWidth()
        .aspectRatio(1.3f)
        .graphicsLayer {
            shape = RoundedCornerShape(SpacingDefault_16dp)
            clip = true
        }
        .drawBehind { drawRect(color = background) }
        .clickable { item.determineClickAction { onSportSelected(it) } }) {
        if (item.favorite.value) {
            Icon(
                modifier = Modifier
                    .padding(top = SpacingHalf_8dp, end = SpacingHalf_8dp)
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(32.dp)
                    .aspectRatio(1f),
                model = item.imageUrl.loadImageWithCrossfade(),
                contentDescription = ""
            )
            ThesisFitnessBLAutoSizeText(text = item.name, maxFontSize = 16.sp, color = Color.Black)
        }
    }
}