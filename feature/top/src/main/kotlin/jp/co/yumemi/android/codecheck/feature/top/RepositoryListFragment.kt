package jp.co.yumemi.android.codecheck.feature.top

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo
import jp.co.yumemi.android.codecheck.feature.top.databinding.FragmentRepositoryListBinding
import jp.co.yumemi.android.codecheck.presentation.autoCleared
import jp.co.yumemi.android.codecheck.presentation.extension.collectWithLifecycle

/**
 * Githubリポジトリのリスト画面。
 */
@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val topViewModel by activityViewModels<TopViewModel>()
    private val viewModel by viewModels<RepositoryListViewModel>()

    private var binding: FragmentRepositoryListBinding by autoCleared()
    private lateinit var adapter: RepositoryListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryListBinding.bind(view)

        setupRecyclerView()
        setupSearchInput()
        observeViewModelState()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        adapter = RepositoryListAdapter { searchedRepositoryItemInfo ->
            navigateToRepositoryDetailFragment(searchedRepositoryItemInfo)
        }

        binding.recyclerView.run {
            this.layoutManager = layoutManager
            this.addItemDecoration(dividerItemDecoration)
            this.adapter = adapter
        }
    }

    private fun setupSearchInput() {
        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                val query = editText.text.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.searchRepositories(query)
                    topViewModel.onSearched()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun observeViewModelState() {
        viewModel.uiState.collectWithLifecycle(this) { state ->
            when (state) {
                is RepositoryListUiState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.errorView.isVisible = false
                }

                is RepositoryListUiState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.errorView.isVisible = false
                    adapter.submitList(state.repositories)
                }

                is RepositoryListUiState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.errorView.isVisible = true
                    binding.errorMessage.text = state.message
                }

                is RepositoryListUiState.Empty -> {
                    binding.progressBar.isVisible = false
                    binding.errorView.isVisible = true
                    binding.errorMessage.text = getString(R.string.no_results_found)
                }
            }
        }
    }

    private fun navigateToRepositoryDetailFragment(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
        val action = RepositoryListFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(searchedRepositoryItemInfo)
        findNavController().navigate(action)
    }
}
