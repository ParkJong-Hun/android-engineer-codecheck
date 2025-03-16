package jp.co.yumemi.android.codecheck.feature.top

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailUiEvent
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailUiState
import jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel.RepositoryDetailViewModel
import jp.co.yumemi.android.codecheck.presentation.time.NowProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RepositoryDetailViewModel
    private lateinit var appMiddleware: Middleware<AppState, AppIntent>
    private lateinit var nowProvider: NowProvider
    private val mockDateTime = LocalDateTime.of(2023, 1, 1, 12, 0)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        appMiddleware = mockk(relaxed = true)
        nowProvider = mockk {
            every { localDateTimeNow() } returns mockDateTime
        }
        viewModel = RepositoryDetailViewModel(appMiddleware, nowProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is None`() = runTest {
        // Then
        val initialState = viewModel.uiState.value
        assertTrue(initialState is RepositoryDetailUiState.None)
    }

    @Test
    fun `OnRepositoryDetailCreate event records history and updates state to Success`() = runTest {
        // Given
        val repository = MOCK_REPOSITORY
        val historySlot = slot<AppIntent.RecordHistory>()

        // When
        viewModel.uiEvent.tryEmit(RepositoryDetailUiEvent.OnRepositoryDetailCreate(repository))
        advanceUntilIdle()

        // Then
        verify { appMiddleware.conveyIntention(capture(historySlot)) }
        val capturedIntent = historySlot.captured
        val history = capturedIntent.history

        assertEquals(repository, history.openedSearchedRepository)
        assertEquals(mockDateTime, history.openedDateTime)
        val currentState = viewModel.uiState.value
        assertTrue(currentState is RepositoryDetailUiState.Success)
        assertEquals(repository, (currentState as RepositoryDetailUiState.Success).repository)
    }

    @Test
    fun `multiple OnRepositoryDetailCreate events generate unique ids for each history record`() =
        runTest {
            // Given
            val repository = MOCK_REPOSITORY
            val historySlots = mutableListOf<AppIntent.RecordHistory>()

            // When
            viewModel.uiEvent.tryEmit(RepositoryDetailUiEvent.OnRepositoryDetailCreate(repository))
            advanceUntilIdle()

            viewModel.uiEvent.tryEmit(RepositoryDetailUiEvent.OnRepositoryDetailCreate(repository))
            advanceUntilIdle()

            // Then
            verify(exactly = 2) {
                appMiddleware.conveyIntention(capture(historySlots))
            }

            assertEquals(2, historySlots.size)
            val history1 = historySlots[0].history
            val history2 = historySlots[1].history
            assert(history1.id != history2.id)
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
