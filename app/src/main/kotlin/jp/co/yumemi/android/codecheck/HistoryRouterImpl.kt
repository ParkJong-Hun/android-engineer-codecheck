package jp.co.yumemi.android.codecheck

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.feature.history.HistoryRouter

class HistoryRouterImpl : HistoryRouter {
    override fun navigateToRepositoryDetail(
        navController: NavController,
        history: History,
    ) {
        val action = NavGraphMainDirections
            .actionToRepositoryDetailFragment(item = history.openedSearchedRepository)
        navController.navigate(action)
    }
}
