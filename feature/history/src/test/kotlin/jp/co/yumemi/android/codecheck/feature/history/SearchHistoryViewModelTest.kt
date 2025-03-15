package jp.co.yumemi.android.codecheck.feature.history

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class SearchHistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var appStateMiddleware: Middleware<AppState, AppIntent>
    private lateinit var appStateFlow: MutableStateFlow<AppState>
    private lateinit var viewModel: SearchHistoryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        appStateFlow = MutableStateFlow(AppState(histories = emptySet()))
        appStateMiddleware = mockk {
            every { businessState } returns appStateFlow as StateFlow<AppState>
        }
        viewModel = SearchHistoryViewModel(appStateMiddleware)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when histories is empty then uiState should be Empty`() = runTest {
        val appState = AppState(histories = emptySet())

        appStateFlow.value = appState

        viewModel.uiState.test {
            val state = awaitItem()
            assert(state is SearchHistoryUiState.Empty)
        }
    }

    @Test
    fun `when appState changes from empty to non-empty, uiState should change accordingly`() =
        runTest {
            val emptyAppState = AppState(histories = emptySet())
            appStateFlow.value = emptyAppState

            val nonEmptyHistories = mockHistories

            viewModel.uiState.test {
                val initialState = awaitItem()
                assert(initialState is SearchHistoryUiState.Empty)

                appStateFlow.value = AppState(histories = nonEmptyHistories)

                val updatedState = awaitItem()
                assert(updatedState is SearchHistoryUiState.Idle)
                val idleState = updatedState as SearchHistoryUiState.Idle
                assertEquals(idleState.histories.toSet(), nonEmptyHistories)
            }
        }

    @Test
    fun `when OnClickHistory event is emitted, uiState should update with clicked history`() =
        runTest {
            viewModel.uiState.test {
                // Given
                val history = mockHistories.first()
                appStateFlow.value = AppState(histories = mockHistories)

                // Initial state should be Idle with histories
                val initialState = awaitItem()
                assert(initialState is SearchHistoryUiState.Idle)

                // When
                viewModel.uiEvent.emit(SearchHistoryUiEvent.OnClickHistory(history))

                // Then
                val updatedState = awaitItem()
                assert(updatedState is SearchHistoryUiState.Idle)
                val idleState = updatedState as SearchHistoryUiState.Idle
                assertEquals(history to true, idleState.onClickedHistory)
            }
        }

    @Test
    fun `collectAppStateMiddleware should update uiState`() = runTest {
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assert(initialState is SearchHistoryUiState.Empty)

            // Update AppState with non-empty histories
            appStateFlow.value = AppState(histories = mockHistories)

            // Bug: collectAppStateMiddleware is not updating _uiState
            // This test will fail with the current implementation
            val updatedState = awaitItem()
            assert(updatedState is SearchHistoryUiState.Idle)
        }
    }

    companion object {
        private val mockHistories = setOf(
            History(
                id = "1",
                openedDateTime = LocalDateTime.of(2025, 3, 11, 22, 8, 32, 0),
                openedSearchedRepository = SearchedRepository(
                    name = "kotlin/kotlinx.coroutines",
                    ownerIconUrl = "https://avatar.url/kotlin",
                    language = "Kotlin",
                    stargazersCount = 12500,
                    watchersCount = 450,
                    forksCount = 1800,
                    openIssuesCount = 120
                )
            )
        )
    }
}
