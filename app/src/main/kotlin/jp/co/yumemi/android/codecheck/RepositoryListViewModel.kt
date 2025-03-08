/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

/**
 * [RepositoryListFragment]のViewModel。
 */
@HiltViewModel
class RepositoryListViewModel @Inject constructor() : ViewModel() {

    /**
     * 入力した値を使って、Github Repositoriesを取得し、アプリで使う情報に加工して返す。
     * @param inputText 入力した文字列
     * @return [List] [SearchedRepositoryItemInfo]のリスト
     */
    fun getSearchedRepositoryItemInfo(inputText: String): List<SearchedRepositoryItemInfo> =
        runBlocking {
            val client = HttpClient(OkHttp)

            return@runBlocking GlobalScope.async {
                val response: HttpResponse =
                    client.get("https://api.github.com/search/repositories") {
                        header("Accept", "application/vnd.github.v3+json")
                        parameter("q", inputText)
                    }

                val jsonBody = JSONObject(response.body<String>())

                val jsonItems = jsonBody.optJSONArray("items")

                val searchedRepositories = mutableListOf<SearchedRepositoryItemInfo>()

                jsonItems?.let { items ->
                    for (i in 0 until items.length()) {
                        val jsonItem = items.optJSONObject(i)
                        val name = jsonItem.optString("full_name")
                        val ownerIconUrl =
                            jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
                        val language = jsonItem.optString("language")
                        val stargazersCount = jsonItem.optLong("stargazers_count")
                        val watchersCount = jsonItem.optLong("watchers_count")
                        val forksCount = jsonItem.optLong("forks_conut")
                        val openIssuesCount = jsonItem.optLong("open_issues_count")

                        searchedRepositories.add(
                            SearchedRepositoryItemInfo(
                                name = name,
                                ownerIconUrl = ownerIconUrl,
                                language = language,
                                stargazersCount = stargazersCount,
                                watchersCount = watchersCount,
                                forksCount = forksCount,
                                openIssuesCount = openIssuesCount
                            )
                        )
                    }
                }
                return@async searchedRepositories.toList()
            }.await()
        }
}
