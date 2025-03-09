package jp.co.yumemi.android.codecheck.domain.repository

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo

interface GithubRepository {
    fun getSearchedRepositoryItemInfo(inputText: String): List<SearchedRepositoryItemInfo>
}
