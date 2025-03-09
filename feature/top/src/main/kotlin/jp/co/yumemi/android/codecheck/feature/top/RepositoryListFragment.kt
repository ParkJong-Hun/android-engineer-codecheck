package jp.co.yumemi.android.codecheck.feature.top

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
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

/**
 * Githubリポジトリのリスト画面。
 */
@AndroidEntryPoint
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val topViewModel by activityViewModels<TopViewModel>()
    private val viewModel by viewModels<RepositoryListViewModel>()

    private var binding: FragmentRepositoryListBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryListBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = RepositoryListAdapter { searchedRepositoryItemInfo ->
            navigateToRepositoryDetailFragment(searchedRepositoryItemInfo)
        }

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.getSearchedRepositoryItemInfo(it).apply {
                            adapter.submitList(this)
                            topViewModel.onSearched()
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.run {
            this.layoutManager = layoutManager
            this.addItemDecoration(dividerItemDecoration)
            this.adapter = adapter
        }
    }

    private fun navigateToRepositoryDetailFragment(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
        val action = RepositoryListFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(searchedRepositoryItemInfo)
        findNavController().navigate(action)
    }
}