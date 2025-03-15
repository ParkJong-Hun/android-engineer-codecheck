@file:Suppress("LongParameterList", "MaxLineLength")

package jp.co.yumemi.android.codecheck.domain.middleware

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchRepositoryMiddlewareTest {

    private lateinit var githubRepository: GithubRepository
    private lateinit var middleware: jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware<SearchRepositoryState, SearchRepositoryIntent>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        githubRepository = mockk()
        middleware = searchRepositoryMiddleware(SearchRepositoryState.None, githubRepository)
    }

    @Test
    fun `initial state should be None`() = runTest(testDispatcher) {
        assertEquals(SearchRepositoryState.None, middleware.businessState.value)
    }

    @Test
    fun `search intent should change state to Loading`() = runTest(testDispatcher) {
        val mockResults = listOf(createMockRepositoryItem())
        coEvery { githubRepository.getSearchedRepositoryItemInfo("kotlin") } returns mockResults

        middleware.conveyIntention(SearchRepositoryIntent.Search("kotlin"))

        assertEquals(SearchRepositoryState.Loading, middleware.businessState.value)
    }

    @Test
    fun `direct SearchComplete intent should update state`() = runTest(testDispatcher) {
        val mockResults = listOf(createMockRepositoryItem())

        middleware.conveyIntention(SearchRepositoryIntent.SearchComplete(mockResults))

        val currentState = middleware.businessState.value
        assertTrue(currentState is SearchRepositoryState.Stable)
        assertEquals(mockResults, (currentState as SearchRepositoryState.Stable).items)
    }

    @Test
    fun `direct Failure intent should update state`() = runTest(testDispatcher) {
        val errorMessage = "Test error"

        middleware.conveyIntention(SearchRepositoryIntent.Failure(RuntimeException(errorMessage)))

        val currentState = middleware.businessState.value
        assertTrue(currentState is SearchRepositoryState.Error)
        assertEquals(errorMessage, (currentState as SearchRepositoryState.Error).message)
    }

    companion object {
        private fun createMockRepositoryItem(
            name: String = "repository-name",
            ownerIconUrl: String = "https://example.com/avatar.png",
            language: String = "Kotlin",
            stargazersCount: Long = 1000L,
            watchersCount: Long = 100L,
            forksCount: Long = 200L,
            openIssuesCount: Long = 50L
        ): SearchedRepository {
            return SearchedRepository(
                name = name,
                ownerIconUrl = ownerIconUrl,
                language = language,
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount
            )
        }
    }
}
