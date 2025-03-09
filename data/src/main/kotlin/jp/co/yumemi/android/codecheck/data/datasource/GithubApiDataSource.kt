package jp.co.yumemi.android.codecheck.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.co.yumemi.android.codecheck.data.IoDispatcher
import jp.co.yumemi.android.codecheck.data.model.GithubSearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubApiDataSource @Inject constructor(
    private val httpClient: HttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun searchRepositories(query: String): GithubSearchResponse {
        return withContext(ioDispatcher) {
            httpClient
                .get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", query)
                }
                .body()
        }
    }
}
