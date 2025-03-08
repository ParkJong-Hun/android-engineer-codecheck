/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil3.load
import jp.co.yumemi.android.codecheck.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryDetailBinding

/**
 * GithubのRepository詳細画面
 */
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentRepositoryDetailBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl);
        binding.nameView.text = item.name;
        binding.languageView.text = item.language;
        binding.starsView.text = "${item.stargazersCount} stars";
        binding.watchersView.text = "${item.watchersCount} watchers";
        binding.forksView.text = "${item.forksCount} forks";
        binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
