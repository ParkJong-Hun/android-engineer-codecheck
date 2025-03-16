package jp.co.yumemi.android.codecheck.presentation.extension

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafely(
    navDirectionsMasterFragmentId: Int,
    navDirections: NavDirections,
) {
    if (currentDestination?.id == navDirectionsMasterFragmentId) {
        navigate(navDirections)
    } else {
        Log.w(
            "NavController",
            "cannot navigate to $navDirections from current destination $currentDestination",
        )
    }
}
