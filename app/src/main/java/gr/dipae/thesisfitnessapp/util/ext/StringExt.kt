package gr.dipae.thesisfitnessapp.util.ext

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

@Composable
fun String.loadImageWithCrossfade(@DrawableRes placeholder: Int? = null): ImageRequest {
    return if (placeholder != null) {
        ImageRequest.Builder(LocalContext.current)
            .placeholder(placeholder)
            .error(placeholder)
            .data(this)
            .crossfade(true)
            .build()
    } else {
        ImageRequest.Builder(LocalContext.current)
            .data(this)
            .crossfade(true)
            .build()
    }
}