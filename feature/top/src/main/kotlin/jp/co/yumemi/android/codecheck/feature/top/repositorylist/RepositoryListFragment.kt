package jp.co.yumemi.android.codecheck.feature.top.repositorylist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.feature.top.R
import jp.co.yumemi.android.codecheck.feature.top.databinding.FragmentRepositoryListBinding
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListUiEvent
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListUiState
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListViewModel
import jp.co.yumemi.android.codecheck.feature.top.router.TopRouter
import jp.co.yumemi.android.codecheck.presentation.autoCleared
import jp.co.yumemi.android.codecheck.presentation.extension.collectWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

/**
 * Githubリポジトリのリスト画面。
 */
@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val viewModel by viewModels<RepositoryListViewModel>()
    private var binding: FragmentRepositoryListBinding by autoCleared()
    private lateinit var repositoryListAdapter: RepositoryListAdapter

    @Inject
    lateinit var topRouter: TopRouter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryListBinding.bind(view)
        setupRecyclerView()
        setupSearchInput()
        observeViewModelState()
        setupGoHistoryButton()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)

        repositoryListAdapter = RepositoryListAdapter { searchedRepositoryItemInfo ->
            viewModel.uiEvent.tryEmit(
                RepositoryListUiEvent.OnClickSearchedRepository(searchedRepositoryItemInfo)
            )
        }

        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
            adapter = repositoryListAdapter
        }
    }

    private fun setupSearchInput() {
        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnSearchClick(editText.text))
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setupGoHistoryButton() {
        binding.goHistoryButton.setOnClickListener {
            viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickGoHistory)
        }
    }

    private fun observeViewModelState() {
        viewModel.uiState.collectWithLifecycle(this) { state ->
            with(binding) {
                when (state) {
                    is RepositoryListUiState.None -> {
                        progressBar.isVisible = false
                        errorView.isVisible = false
                        goHistoryButton.isVisible = false
                    }

                    is RepositoryListUiState.Loading -> {
                        progressBar.isVisible = true
                        errorView.isVisible = false
                    }

                    is RepositoryListUiState.Stable.Success -> {
                        progressBar.isVisible = false
                        errorView.isVisible = false
                        repositoryListAdapter.submitList(state.repositories)
                    }

                    is RepositoryListUiState.Stable.Empty -> {
                        progressBar.isVisible = false
                        errorView.isVisible = true
                        errorMessage.text = getString(R.string.no_results_found)
                    }

                    is RepositoryListUiState.Error -> {
                        progressBar.isVisible = false
                        errorView.isVisible = true
                        errorMessage.text = state.message
                    }
                }
            }
        }

        viewModel.uiState
            .filter { it is RepositoryListUiState.Stable }
            .distinctUntilChanged()
            .collectWithLifecycle(this) { state ->
                val stableState = state as RepositoryListUiState.Stable
                if (stableState.onClickedGoHistory) {
                    viewModel.uiEvent.tryEmit(RepositoryListUiEvent.FinishNavigateToSearchHistory)
                    topRouter.navigateToSearchHistory(findNavController())
                }
            }

        viewModel.uiState
            .filter { it is RepositoryListUiState.Stable.Success }
            .distinctUntilChanged()
            .collectWithLifecycle(this) { state ->
                val stableState = state as RepositoryListUiState.Stable.Success
                val (searchedRepository, hasClickedSearchedRepository) = stableState.onClickedSearchedRepository
                if (hasClickedSearchedRepository) {
                    viewModel.uiEvent.tryEmit(RepositoryListUiEvent.FinishNavigateToRepositoryDetail)
                    topRouter.navigateToRepositoryDetail(
                        navController = findNavController(),
                        item = requireNotNull(searchedRepository),
                    )
                }
            }
    }
}
