package jp.co.yumemi.android.codecheck

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.RepositoryDetailFragment
import jp.co.yumemi.android.codecheck.feature.top.RepositoryDetailFragmentArgs
import jp.co.yumemi.android.codecheck.feature.top.RepositoryListFragmentDirections
import jp.co.yumemi.android.codecheck.feature.top.TopRouter

class TopRouterImpl : TopRouter {
    override fun navigateToSearchHistory(navController: NavController) {
        val action = RepositoryListFragmentDirections
            .actionRepositoryListFragmentToHistoryFragment()
        navController.navigate(action)
    }

    override fun navigateToRepositoryDetail(
        navController: NavController,
        item: SearchedRepository
    ) {
        val action = RepositoryListFragmentDirections
            .actionRepositoryListFragmentToRepositoryDetailFragment(item)
        navController.navigate(action)
    }

    override fun getItemFromArgs(repositoryDetailFragment: RepositoryDetailFragment): SearchedRepository {
        val args: RepositoryDetailFragmentArgs by repositoryDetailFragment.navArgs()
        return args.item
    }
}
