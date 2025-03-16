package jp.co.yumemi.android.codecheck.feature.top

import io.mockk.coEvery
import io.mockk.spyk
import io.mockk.verify
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryIntent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryState
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListUiEvent
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListUiState
import jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel.RepositoryListViewModel
import jp.co.yumemi.android.codecheck.testing.domain.middlewaare.MockMiddleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
    fun `initial state is None`() = runTest(testDispatcher) {
        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.None)
    }

    @Test
    fun `searchRepositories transitions from None to Loading to Success`() =
        runTest(testDispatcher) {
            // Given
            val query = "kotlin"
            val mockRepositories = listOf(MOCK_REPOSITORY)

            // When - simulate the state transitions
            viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnSearchClick(query))
            // Verify the Loading state
            searchRepositoryMiddleware.updatableBusinessState.value = SearchRepositoryState.Loading
            advanceUntilIdle()
            assert(viewModel.uiState.value is RepositoryListUiState.Loading)

            // Then move to stable state
            searchRepositoryMiddleware.updatableBusinessState.value =
                SearchRepositoryState.Stable(mockRepositories)
            advanceUntilIdle()

            // Assert final state
            val currentState = viewModel.uiState.value
            assert(currentState is RepositoryListUiState.Stable.Success)
            assertEquals(
                mockRepositories,
                (currentState as RepositoryListUiState.Stable.Success).repositories,
            )
        }

    @Test
    fun `OnClickSearchedRepository updates state correctly`() = runTest(testDispatcher) {
        // Given - set up a success state with repositories
        val mockRepositories = listOf(MOCK_REPOSITORY)
        searchRepositoryMiddleware.updatableBusinessState.value =
            SearchRepositoryState.Stable(mockRepositories)
        advanceUntilIdle()

        // When - emit click event
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickSearchedRepository(MOCK_REPOSITORY))
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
        assertEquals(MOCK_REPOSITORY, currentState.onClickedSearchedRepository.first)
        assertTrue(currentState.onClickedSearchedRepository.second)
    }

    @Test
    fun `FinishNavigateToRepositoryDetail resets repository selection state`() =
        runTest(testDispatcher) {
            // Given - set up a success state with repositories and selected repository
            val mockRepositories = listOf(MOCK_REPOSITORY)
            searchRepositoryMiddleware.updatableBusinessState.value =
                SearchRepositoryState.Stable(mockRepositories)
            advanceUntilIdle()

            // Select a repository
            viewModel.uiEvent.tryEmit(
                RepositoryListUiEvent.OnClickSearchedRepository(
                    MOCK_REPOSITORY,
                ),
            )
            advanceUntilIdle()

            // Verify selection happened
            var currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
            assertNotNull(currentState.onClickedSearchedRepository.first)
            assertTrue(currentState.onClickedSearchedRepository.second)

            // When - finish navigation
            viewModel.uiEvent.tryEmit(RepositoryListUiEvent.FinishNavigateToRepositoryDetail)
            advanceUntilIdle()

            // Then - selection should be reset
            currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
            assertNull(currentState.onClickedSearchedRepository.first)
            assertFalse(currentState.onClickedSearchedRepository.second)
        }

    @Test
    fun `OnClickGoHistory updates goHistory flag in Success state`() = runTest(testDispatcher) {
        // Given - set up a success state with repositories
        val mockRepositories = listOf(MOCK_REPOSITORY)
        searchRepositoryMiddleware.updatableBusinessState.value =
            SearchRepositoryState.Stable(mockRepositories)
        advanceUntilIdle()

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickGoHistory)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
        assertTrue(currentState.onClickedGoHistory)
    }

    @Test
    fun `OnClickGoHistory updates goHistory flag in Empty state`() = runTest(testDispatcher) {
        // Given - set up an empty stable state
        searchRepositoryMiddleware.updatableBusinessState.value =
            SearchRepositoryState.Stable(emptyList())
        advanceUntilIdle()

        // Verify we're in Empty state
        val initialState = viewModel.uiState.value
        assert(initialState is RepositoryListUiState.Stable.Empty)

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickGoHistory)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Empty
        assertTrue(currentState.onClickedGoHistory)
    }

    @Test
    fun `OnClickGoHistory does nothing in non-Stable states`() = runTest(testDispatcher) {
        // Given - we're in Loading state
        searchRepositoryMiddleware.updatableBusinessState.value = SearchRepositoryState.Loading
        advanceUntilIdle()

        // Verify we're in Loading state
        val initialState = viewModel.uiState.value
        assert(initialState is RepositoryListUiState.Loading)

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickGoHistory)
        advanceUntilIdle()

        // Then - state should remain as Loading
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.Loading)
    }

    @Test
    fun `FinishNavigateToSearchHistory resets goHistory flag`() = runTest(testDispatcher) {
        // Given - set up a success state with repositories and goHistory=true
        val mockRepositories = listOf(MOCK_REPOSITORY)
        searchRepositoryMiddleware.updatableBusinessState.value =
            SearchRepositoryState.Stable(mockRepositories)
        advanceUntilIdle()

        // Set goHistory flag
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnClickGoHistory)
        advanceUntilIdle()

        // Verify flag is set
        var currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
        assertTrue(currentState.onClickedGoHistory)

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.FinishNavigateToSearchHistory)
        advanceUntilIdle()

        // Then
        currentState = viewModel.uiState.value as RepositoryListUiState.Stable.Success
        assertFalse(currentState.onClickedGoHistory)
    }

    @Test
    fun `searchRepositories with loading state returns loading state`() = runTest(testDispatcher) {
        // Given
        val query = "kotlin"

        coEvery { searchRepositoryMiddleware.conveyIntention(SearchRepositoryIntent.Search(query)) } answers {
            searchRepositoryMiddleware.updatableBusinessState.value = SearchRepositoryState.Loading
        }

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnSearchClick(query))
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is RepositoryListUiState.Loading)
    }

    @Test
    fun `searchRepositories trims whitespace from query`() = runTest(testDispatcher) {
        // Given
        val queryWithWhitespace = "  kotlin  "
        val trimmedQuery = "kotlin"

        // When
        viewModel.uiEvent.tryEmit(RepositoryListUiEvent.OnSearchClick(queryWithWhitespace))
        advanceUntilIdle()

        // Then
        verify {
            searchRepositoryMiddleware.conveyIntention(
                SearchRepositoryIntent.Search(
                    trimmedQuery,
                ),
            )
        }
    }

    companion object {
        private val MOCK_REPOSITORY = SearchedRepository(
            name = "kotlin/kotlin",
            ownerIconUrl = "https://avatar.url",
            language = "Kotlin",
            stargazersCount = 1000,
            watchersCount = 100,
            forksCount = 200,
            openIssuesCount = 50,
        )
    }
}
