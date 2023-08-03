package gr.dipae.thesisfitnessapp.ui.sport.session.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import gr.dipae.thesisfitnessapp.R


@Composable
fun LottieCountDownAnimationOverlay(
    modifier: Modifier,
    animationVisibility: Boolean,
    onStartAnimationComplete: OnStartAnimationComplete
) {
    if (animationVisibility) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.countdown_animation))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            modifier = modifier,
            composition = composition
        )
        LaunchedEffect(key1 = progress) {
            if (progress == 1f) {
                onStartAnimationComplete()
            }
        }
    }
}