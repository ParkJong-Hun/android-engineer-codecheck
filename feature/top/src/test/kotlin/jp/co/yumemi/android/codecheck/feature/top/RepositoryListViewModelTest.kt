@file:Suppress("ForbiddenComment", "UnusedPrivateProperty")

package jp.co.yumemi.android.codecheck.feature.top

import io.mockk.coEvery
import io.mockk.spyk
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryIntent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryState
import jp.co.yumemi.android.codecheck.testing.domain.middlewaare.MockMiddleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RepositoryListViewModel
    private lateinit var searchRepositoryMiddleware: MockMiddleware<SearchRepositoryState, SearchRepositoryIntent>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchRepositoryMiddleware = spyk(MockMiddleware(SearchRepositoryState.None))
        viewModel = RepositoryListViewModel(searchRepositoryMiddleware)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchRepositories with valid query returns success state`() = runTest {
        // Given
        val query = "kotlin"
        val mockRepositories = listOf(MOCK_REPOSITORY)
        val mockBusinessState = SearchRepositoryState.Stable(mockRepositories)

        coEvery { searchRepositoryMiddleware.conveyIntention(SearchRepositoryIntent.Search(query)) } answers {
            searchRepositoryMiddleware.updatableBusinessState.value = mockBusinessState
        }

        // When
        viewModel.onSearchClick(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.Success)
        assertEquals(mockRepositories, (currentState as RepositoryListUiState.Success).repositories)
    }

    @Test
    fun `searchRepositories with empty result returns empty state`() = runTest {
        // Given
        val query = "nonexistentrepository12345"

        coEvery { searchRepositoryMiddleware.conveyIntention(SearchRepositoryIntent.Search(query)) } answers {
            searchRepositoryMiddleware.updatableBusinessState.value =
                SearchRepositoryState.Stable(emptyList())
        }

        // When
        viewModel.onSearchClick(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.Empty)
    }

    @Test
    fun `searchRepositories with error returns error state`() = runTest {
        // Given
        val query = "kotlin"
        val errorMessage = "Network error"

        coEvery {
            searchRepositoryMiddleware.conveyIntention(SearchRepositoryIntent.Search(query))
        } answers {
            searchRepositoryMiddleware.updatableBusinessState.value =
                SearchRepositoryState.Error(errorMessage)
        }

        // When
        viewModel.onSearchClick(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.Error)
        assertEquals(errorMessage, (currentState as RepositoryListUiState.Error).message)
    }

    @Test
    fun `searchRepositories with empty query does nothing`() = runTest {
        // Given
        val initialState = viewModel.uiState.value

        // When
        viewModel.onSearchClick("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(initialState, viewModel.uiState.value)
        assert(initialState is RepositoryListUiState.None)
    }

    companion object {
        private val MOCK_REPOSITORY = SearchedRepository(
            name = "kotlin/kotlin",
            ownerIconUrl = "https://avatar.url",
            language = "Kotlin",
            stargazersCount = 1000,
            watchersCount = 100,
            forksCount = 200,
            openIssuesCount = 50
        )
    }
}
