package jp.co.yumemi.android.codecheck.feature.top.repositorydetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil3.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.R
import jp.co.yumemi.android.codecheck.feature.top.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailUiEvent
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailUiState
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailViewModel
import jp.co.yumemi.android.codecheck.feature.top.router.TopRouter
import jp.co.yumemi.android.codecheck.presentation.autoCleared
import jp.co.yumemi.android.codecheck.presentation.extension.collectWithLifecycle
import javax.inject.Inject

/**
 * GithubのRepository詳細画面
 */
@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val viewModel: RepositoryDetailViewModel by viewModels()
    private var binding: FragmentRepositoryDetailBinding by autoCleared()

    @Inject
    lateinit var topRouter: TopRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = topRouter.getItemFromArgs(this)
        viewModel.uiEvent.tryEmit(RepositoryDetailUiEvent.OnRepositoryDetailCreate(repository))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailBinding.bind(view)
        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState.collectWithLifecycle(this) { state ->
            when (state) {
                is RepositoryDetailUiState.None -> {
                    // no-op
                }

                is RepositoryDetailUiState.Success -> {
                    bindRepositoryData(state.repository)
                }
            }
        }
    }

    private fun bindRepositoryData(repository: SearchedRepository) {
        with(binding) {
            ownerIconView.load(repository.ownerIconUrl)
            nameView.text = repository.name
            languageView.text = getString(R.string.written_language, repository.language)
            watchersView.text = getString(R.string.watchers, repository.watchersCount)
            forksView.text = getString(R.string.forks, repository.forksCount)
            openIssuesView.text = getString(R.string.open_issues, repository.openIssuesCount)
            starsView.text = getString(R.string.stars, repository.stargazersCount)
        }
    }
}
