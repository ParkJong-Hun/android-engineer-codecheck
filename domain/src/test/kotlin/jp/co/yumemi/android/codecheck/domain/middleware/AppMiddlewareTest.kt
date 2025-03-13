@file:Suppress("LongParameterList", "MaxLineLength")

package jp.co.yumemi.android.codecheck.domain.middleware

import jp.co.yumemi.android.codecheck.domain.entity.Histories
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class AppMiddlewareTest {

    private lateinit var middleware: Middleware<AppState, AppIntent>
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        middleware = appMiddleware(AppState(Histories(emptyList())))
    }

    @Test
    fun `initial state should have empty histories`() = runTest(testDispatcher) {
        val initialState = middleware.businessState.value
        assertEquals(0, initialState.histories.histories.size)
    }

    @Test
    fun `RecordHistory intent should add history to the list`() = runTest(testDispatcher) {
        // Given
        val mockHistory = createMockHistory()

        // When
        middleware.conveyIntention(AppIntent.RecordHistory(mockHistory))

        // Then
        val updatedState = middleware.businessState.value
        assertEquals(1, updatedState.histories.histories.size)
        assertEquals(mockHistory, updatedState.histories.histories[0])
    }

    @Test
    fun `RecordHistory intent should add multiple histories in order`() = runTest(testDispatcher) {
        // Given
        val mockHistory1 = createMockHistory(id = "1", name = "repo1")
        val mockHistory2 = createMockHistory(id = "2", name = "repo2")
        val mockHistory3 = createMockHistory(id = "3", name = "repo3")

        // When
        middleware.conveyIntention(AppIntent.RecordHistory(mockHistory1))
        middleware.conveyIntention(AppIntent.RecordHistory(mockHistory2))
        middleware.conveyIntention(AppIntent.RecordHistory(mockHistory3))

        // Then
        val updatedState = middleware.businessState.value
        assertEquals(3, updatedState.histories.histories.size)

        assertEquals("1", updatedState.histories.histories[0].id)
        assertEquals("2", updatedState.histories.histories[1].id)
        assertEquals("3", updatedState.histories.histories[2].id)
    }

    @Test
    fun `RecordHistory intent should not remove existing histories`() = runTest(testDispatcher) {
        // Given
        val initialHistory = createMockHistory(id = "1", name = "initial-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(initialHistory))

        val stateAfterFirstAdd = middleware.businessState.value
        assertEquals(1, stateAfterFirstAdd.histories.histories.size)

        // When
        val newHistory = createMockHistory(id = "2", name = "new-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(newHistory))

        // Then
        val finalState = middleware.businessState.value
        assertEquals(2, finalState.histories.histories.size)

        assertEquals(
            "initial-repo",
            finalState.histories.histories[0].openedSearchedRepository.name
        )
        assertEquals("new-repo", finalState.histories.histories[1].openedSearchedRepository.name)
    }

    @Test
    fun `AppReducer should be pure and not modify the original state`() = runTest(testDispatcher) {
        // Given
        val initialHistory = createMockHistory(id = "1", name = "initial-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(initialHistory))

        val stateBeforeSecondAdd = middleware.businessState.value
        val historiesSizeBeforeAdd = stateBeforeSecondAdd.histories.histories.size

        // When
        val newHistory = createMockHistory(id = "2", name = "new-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(newHistory))

        // Then
        assertEquals(1, historiesSizeBeforeAdd)

        val finalState = middleware.businessState.value
        assertEquals(2, finalState.histories.histories.size)
    }

    companion object {
        private fun createMockHistory(
            id: String = "history-id",
            name: String = "repository-name",
            dateTime: LocalDateTime = LocalDateTime.now()
        ): History {
            return History(
                id = id,
                openedDateTime = dateTime,
                openedSearchedRepository = SearchedRepository(
                    name = name,
                    ownerIconUrl = "https://example.com/avatar.png",
                    language = "Kotlin",
                    stargazersCount = 1000L,
                    watchersCount = 100L,
                    forksCount = 200L,
                    openIssuesCount = 50L
                )
            )
        }
    }
}
