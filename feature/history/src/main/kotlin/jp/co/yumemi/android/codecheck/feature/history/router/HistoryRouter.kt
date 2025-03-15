package jp.co.yumemi.android.codecheck.feature.history.router

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.domain.entity.History

interface HistoryRouter {
    fun navigateToRepositoryDetail(navController: NavController, history: History)
}
