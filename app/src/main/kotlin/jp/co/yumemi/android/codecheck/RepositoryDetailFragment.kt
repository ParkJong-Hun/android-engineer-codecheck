/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil3.load
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryDetailBinding

/**
 * GithubのRepository詳細画面
 */
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val topViewModel by activityViewModels<TopViewModel>()

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailBinding.bind(view).also {
            it.bind(args.item)
        }

        topViewModel.lastSearchDate.collect(this) {
            Log.d("検索した日時", it.toString())
        }
    }

    private fun FragmentRepositoryDetailBinding.bind(item: SearchedRepositoryItemInfo) {
        ownerIconView.load(item.ownerIconUrl)
        nameView.text = item.name
        languageView.text = getString(R.string.written_language, item.language)
        starsView.text = getString(R.string.stars, item.stargazersCount)
        watchersView.text = getString(R.string.watchers, item.watchersCount)
        forksView.text = getString(R.string.forks, item.forksCount)
        openIssuesView.text = getString(R.string.open_issues, item.openIssuesCount)
    }
}
