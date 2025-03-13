package jp.co.yumemi.android.codecheck.feature.history

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.android.codecheck.domain.entity.Histories
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
        appStateFlow = MutableStateFlow(AppState(histories = Histories(emptyList())))
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
        val emptyHistories = Histories(emptyList())
        val appState = AppState(histories = emptyHistories)

        appStateFlow.value = appState

        viewModel.uiState.test {
            val state = awaitItem()
            assert(state is SearchHistoryUiState.Empty)
        }
    }

    @Test
    fun `when appState changes from empty to non-empty, uiState should change accordingly`() =
        runTest {
            val emptyHistories = Histories(emptyList())
            val emptyAppState = AppState(histories = emptyHistories)
            appStateFlow.value = emptyAppState

            val nonEmptyHistories = mockHistories

            viewModel.uiState.test {
                val initialState = awaitItem()
                assert(initialState is SearchHistoryUiState.Empty)

                appStateFlow.value = AppState(histories = nonEmptyHistories)

                val updatedState = awaitItem()
                assert(updatedState is SearchHistoryUiState.Idle)
                val idleState = updatedState as SearchHistoryUiState.Idle
                assertEquals(idleState.histories, nonEmptyHistories)
            }
        }

    companion object {
        private val mockHistories = Histories(
            listOf(
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
        )
    }
}
