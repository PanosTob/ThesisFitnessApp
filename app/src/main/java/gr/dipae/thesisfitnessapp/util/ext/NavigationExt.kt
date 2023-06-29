package gr.dipae.thesisfitnessapp.util.ext

import androidx.navigation.NavController
import androidx.navigation.NavOptions

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