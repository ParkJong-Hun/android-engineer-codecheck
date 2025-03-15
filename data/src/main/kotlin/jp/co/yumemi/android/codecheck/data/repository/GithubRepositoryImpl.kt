@file:Suppress("ForbiddenComment")

package jp.co.yumemi.android.codecheck.data.repository

import jp.co.yumemi.android.codecheck.data.datasource.GithubApiDataSource
import jp.co.yumemi.android.codecheck.data.IoDispatcher
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val githubApiDataSource: GithubApiDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GithubRepository {
    override suspend fun getSearchedRepositoryItemInfo(query: String): List<SearchedRepository> {
        return withContext(ioDispatcher) {
            val response = githubApiDataSource.searchRepositories(query)

            response.items.map { item ->
                SearchedRepository(
                    name = item.fullName,
                    ownerIconUrl = item.owner.avatarUrl,
                    language = item.language ?: "",
                    stargazersCount = item.stargazersCount,
                    watchersCount = item.watchersCount,
                    forksCount = item.forksCount,
                    openIssuesCount = item.openIssuesCount
                )
            }
        }
    }
}
