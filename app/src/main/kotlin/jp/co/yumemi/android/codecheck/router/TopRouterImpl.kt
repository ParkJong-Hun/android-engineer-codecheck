package jp.co.yumemi.android.codecheck.router

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.RepositoryDetailFragment
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.RepositoryDetailFragmentArgs
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.RepositoryListFragmentDirections
import jp.co.yumemi.android.codecheck.feature.top.router.TopRouter
import jp.co.yumemi.android.codecheck.presentation.extension.navigateSafely

class TopRouterImpl : TopRouter {
    override fun navigateToSearchHistory(navController: NavController) {
        val action = RepositoryListFragmentDirections
            .actionRepositoryListFragmentToHistoryFragment()
        navController.navigateSafely(R.id.repositoryListFragment, action)
    }

    override fun navigateToRepositoryDetail(
        navController: NavController,
        item: SearchedRepository,
    ) {
        val action = RepositoryListFragmentDirections
            .actionRepositoryListFragmentToRepositoryDetailFragment(item)
        navController.navigateSafely(R.id.repositoryListFragment, action)
    }

    override fun getItemFromArgs(repositoryDetailFragment: RepositoryDetailFragment): SearchedRepository {
        val args: RepositoryDetailFragmentArgs by repositoryDetailFragment.navArgs()
        return args.item
    }
}
