/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryListBinding

/**
 * Githubリポジトリのリスト画面。
 */
class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {
    private val topViewModel by activityViewModels<TopViewModel>()

    private var binding: FragmentRepositoryListBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRepositoryListBinding.bind(view)

        val viewModel = RepositoryListViewModel(requireContext())

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = RepositoryListAdapter(object : RepositoryListAdapter.OnItemClickListener {
            override fun itemClick(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
                navigateToRepositoryDetailFragment(searchedRepositoryItemInfo)
            }
        })

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

    fun navigateToRepositoryDetailFragment(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
        val action = RepositoryListFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(searchedRepositoryItemInfo)
        findNavController().navigate(action)
    }
}
