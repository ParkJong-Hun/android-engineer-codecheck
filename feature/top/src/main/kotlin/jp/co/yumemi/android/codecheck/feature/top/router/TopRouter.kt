package jp.co.yumemi.android.codecheck.feature.top.router

import androidx.navigation.NavController
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.RepositoryDetailFragment

interface TopRouter {
    fun navigateToSearchHistory(navController: NavController)
    fun navigateToRepositoryDetail(navController: NavController, item: SearchedRepository)

    fun getItemFromArgs(repositoryDetailFragment: RepositoryDetailFragment): SearchedRepository
}
