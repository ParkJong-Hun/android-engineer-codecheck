package jp.co.yumemi.android.codecheck.testing.data.repository

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository

class FakeGithubRepository : GithubRepository {
    override suspend fun getSearchedRepositoryItemInfo(query: String): List<SearchedRepositoryItemInfo> {
        return listOf()
    }
}
