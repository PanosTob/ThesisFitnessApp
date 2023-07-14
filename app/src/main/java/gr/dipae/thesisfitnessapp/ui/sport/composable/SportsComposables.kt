package gr.dipae.thesisfitnessapp.ui.sport.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.compose.ThesisFitnessBLAutoSizeText
import gr.dipae.thesisfitnessapp.ui.sport.model.SportsUiState
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondaryDark
import gr.dipae.thesisfitnessapp.ui.theme.SpacingCustom_24dp
import gr.dipae.thesisfitnessapp.ui.theme.SpacingDefault_16dp
import gr.dipae.thesisfitnessapp.util.ext.loadImageWithCrossfade

@Composable
fun SportsContent(
    uiState: SportsUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = SpacingCustom_24dp),
        verticalArrangement = Arrangement.spacedBy(SpacingDefault_16dp)
    ) {
        items(items = uiState.sports, key = { item -> item.id }) { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f)
                    .background(ColorSecondaryDark, RoundedCornerShape(SpacingDefault_16dp))
                    .clip(RoundedCornerShape(SpacingDefault_16dp))
                    .clickable { item.selected.value = !item.selected.value },
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .aspectRatio(1f),
                    model = item.imageUrl.loadImageWithCrossfade(R.drawable.img_general_sport),
                    contentDescription = ""
                )
                ThesisFitnessBLAutoSizeText(text = item.name, color = item.color, maxFontSize = 22.sp)
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .aspectRatio(1f),
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}