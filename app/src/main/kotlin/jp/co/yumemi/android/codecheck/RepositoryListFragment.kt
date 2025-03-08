/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryListBinding

class OneFragment : Fragment(R.layout.fragment_repository_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositoryListBinding.bind(view)

        val viewModel = RepositoryListViewModel(requireContext())

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = RepositoryListAdapter(object : RepositoryListAdapter.OnItemClickListener {
            override fun itemClick(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
                gotoRepositoryFragment(searchedRepositoryItemInfo)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it).apply {
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    fun gotoRepositoryFragment(searchedRepositoryItemInfo: SearchedRepositoryItemInfo) {
        val action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(searchedRepositoryItemInfo)
        findNavController().navigate(action)
    }
}
