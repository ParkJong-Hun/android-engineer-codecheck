package jp.co.yumemi.android.codecheck.feature.top

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil3.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.codecheck.presentation.autoCleared
import jp.co.yumemi.android.codecheck.presentation.extension.collectWithLifecycle

/**
 * GithubのRepository詳細画面
 */
@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val topViewModel by activityViewModels<TopViewModel>()

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailBinding.bind(view).also {
            it.bind(args.item)
        }

        topViewModel.lastSearchDate.collectWithLifecycle(this) {
            Log.d("検索した日時", it.toString())
        }
    }

    private fun FragmentRepositoryDetailBinding.bind(item: SearchedRepository) {
        ownerIconView.load(item.ownerIconUrl)
        nameView.text = item.name
        languageView.text = getString(R.string.written_language, item.language)
        starsView.text = getString(R.string.stars, item.stargazersCount)
        watchersView.text = getString(R.string.watchers, item.watchersCount)
        forksView.text = getString(R.string.forks, item.forksCount)
        openIssuesView.text = getString(R.string.open_issues, item.openIssuesCount)
    }
}
