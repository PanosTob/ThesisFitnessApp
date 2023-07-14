package gr.dipae.thesisfitnessapp.util.ext

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import gr.dipae.thesisfitnessapp.R

/*
 * prevent open 2 times
 */
fun NavController.singleNavigate(destinationRoute: String, singleTop: Boolean = true) {
    if (currentDestination?.route != destinationRoute) {
        navigate(destinationRoute) {
            launchSingleTop = singleTop
        }
    }
}

fun NavController.singleNavigateWithPopInclusive(destinationRoute: String, popRoute: String?) {
    if (currentDestination?.route != destinationRoute) {
        val navOptions = NavOptions.Builder().setPopUpTo(popRoute, true).build()
        navigate(destinationRoute, navOptions)
    }
}

fun NavOptions.Builder.slideNavOptions(): NavOptions.Builder {
    return setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
}

fun List<String>.getComposeNavigationArgs(): String {
    return joinToString(separator = "/", prefix = "/") { "{$it}" }
}