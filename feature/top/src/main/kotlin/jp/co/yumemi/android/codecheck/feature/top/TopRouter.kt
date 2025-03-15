package jp.co.yumemi.android.codecheck.feature.top

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository

interface TopRouter {
    fun navigateToSearchHistory(navController: NavController)
    fun navigateToRepositoryDetail(navController: NavController, item: SearchedRepository)
    fun getItemFromArgs(repositoryDetailFragment: RepositoryDetailFragment): SearchedRepository
}
