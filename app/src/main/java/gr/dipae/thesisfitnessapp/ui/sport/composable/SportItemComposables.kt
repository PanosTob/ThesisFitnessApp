package gr.dipae.thesisfitnessapp.ui.sport.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.sport.model.SportUiItem
import gr.dipae.thesisfitnessapp.ui.sport.navigation.OnSportSelected
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade

@Composable
fun SportItem(
    item: SportUiItem,
    onSportSelected: OnSportSelected
) {
    Column(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .graphicsLayer {
                shape = RoundedCornerShape(SpacingDefault_16dp)
                clip = true
            }
            .drawBehind { drawRect(color = item.backgroundColor) }
            .clickable { item.determineClickAction { onSportSelected(it) } },
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .aspectRatio(1f),
            model = item.imageUrl.loadImageWithCrossfade(R.drawable.img_general_sport),
            contentDescription = ""
        )
        ThesisFitnessBLAutoSizeText(text = item.name, maxFontSize = 22.sp)
    }
}