package jp.co.yumemi.android.codecheck.router

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.feature.history.router.HistoryRouter
import jp.co.yumemi.android.codecheck.feature.history.searchhistory.SearchHistoryFragmentDirections

class HistoryRouterImpl : HistoryRouter {
    override fun navigateToRepositoryDetail(
        navController: NavController,
        history: History,
    ) {
        val action = SearchHistoryFragmentDirections
            .actionSearchHistoryFragmentToRepositoryDetailFragment(item = history.openedSearchedRepository)
        navController.navigate(action)
    }
}
