/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil3.load
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryDetailBinding
import kotlinx.coroutines.launch

/**
 * GithubのRepository詳細画面
 */
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val topViewModel by activityViewModels<TopViewModel>()

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl);
        binding.nameView.text = item.name;
        binding.languageView.text = item.language;
        binding.starsView.text = "${item.stargazersCount} stars";
        binding.watchersView.text = "${item.watchersCount} watchers";
        binding.forksView.text = "${item.forksCount} forks";
        binding.openIssuesView.text = "${item.openIssuesCount} open issues";

        lifecycleScope.launch {
            topViewModel.lastSearchDate.collect {
                Log.d("検索した日時", it.toString())
            }
        }
    }
}
