package jp.co.yumemi.android.codecheck

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.feature.top.TopRouter

class TopRouterImpl : TopRouter {
    override fun navigateToHistory(navController: NavController) {
        val action = NavGraphMainDirections.actionToHistoryFragment()
        navController.navigate(action)
    }
}
