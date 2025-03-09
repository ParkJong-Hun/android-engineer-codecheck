@file:Suppress("ForbiddenComment", "UnusedPrivateProperty")

package jp.co.yumemi.android.codecheck.feature.top

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RepositoryListViewModel
    private lateinit var githubRepository: GithubRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        githubRepository = mockk()
        viewModel = RepositoryListViewModel(githubRepository)
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

        coEvery { githubRepository.getSearchedRepositoryItemInfo(query) } returns mockRepositories

        // When
        viewModel.searchRepositories(query)
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

        coEvery { githubRepository.getSearchedRepositoryItemInfo(query) } returns emptyList()

        // When
        viewModel.searchRepositories(query)
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
            githubRepository.getSearchedRepositoryItemInfo(query)
        } throws IOException(errorMessage)

        // When
        viewModel.searchRepositories(query)
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
        viewModel.searchRepositories("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(initialState, viewModel.uiState.value)
        assert(initialState is RepositoryListUiState.None)
    }

    @Test
    fun `searchRepositories sets state to Loading before making repository call`() = runTest {
        // Given
        val query = "kotlin"
        val collectStates = mutableListOf<RepositoryListUiState>()

        val collectJob = launch {
            viewModel.uiState.collect {
                collectStates.add(it)
            }
        }

        coEvery { githubRepository.getSearchedRepositoryItemInfo(query) } coAnswers {
            delay(1000)
            emptyList()
        }

        // When - 検索
        viewModel.searchRepositories(query)
        testDispatcher.scheduler.advanceUntilIdle()

        collectJob.cancel()

        assert(collectStates.size >= 2)
        assert(collectStates[0] is RepositoryListUiState.None)
        assert(collectStates[1] is RepositoryListUiState.Loading)
    }

    companion object {
        private val MOCK_REPOSITORY = SearchedRepositoryItemInfo(
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
