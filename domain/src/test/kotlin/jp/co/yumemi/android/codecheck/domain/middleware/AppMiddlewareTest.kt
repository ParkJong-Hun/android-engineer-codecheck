package jp.co.yumemi.android.codecheck.domain.middleware

import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class AppMiddlewareTest {

    private lateinit var middleware: Middleware<AppState, AppIntent>
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        middleware = appMiddleware(AppState(emptySet()))
    }

    @Test
    fun `initial state should have empty histories`() = runTest(testDispatcher) {
        val initialState = middleware.businessState.value
        assertEquals(0, initialState.histories.size)
    }

    @Test
    fun `RecordHistory intent should add history to the list`() = runTest(testDispatcher) {
        // Given
        val mockHistory = createMockHistory()

        // When
        middleware.conveyIntention(AppIntent.RecordHistory(mockHistory))

        // Then
        val updatedState = middleware.businessState.value
        assertEquals(1, updatedState.histories.size)
        assert(updatedState.histories.find { it == mockHistory } != null)
    }

    @Test
    fun `RecordHistory intent should add multiple histories`() = runTest(testDispatcher) {
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
        assertEquals(3, updatedState.histories.size)
    }

    @Test
    fun `RecordHistory intent should not remove existing histories`() = runTest(testDispatcher) {
        // Given
        val initialHistory = createMockHistory(id = "1", name = "initial-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(initialHistory))

        val stateAfterFirstAdd = middleware.businessState.value
        assertEquals(1, stateAfterFirstAdd.histories.size)

        // When
        val newHistory = createMockHistory(id = "2", name = "new-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(newHistory))

        // Then
        val finalState = middleware.businessState.value
        assertEquals(2, finalState.histories.size)

        assert(finalState.histories.find { it.openedSearchedRepository.name == "initial-repo" } != null)
        assert(finalState.histories.find { it.openedSearchedRepository.name == "new-repo" } != null)
    }

    @Test
    fun `AppReducer should be pure and not modify the original state`() = runTest(testDispatcher) {
        // Given
        val initialHistory = createMockHistory(id = "1", name = "initial-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(initialHistory))

        val stateBeforeSecondAdd = middleware.businessState.value
        val historiesSizeBeforeAdd = stateBeforeSecondAdd.histories.size

        // When
        val newHistory = createMockHistory(id = "2", name = "new-repo")
        middleware.conveyIntention(AppIntent.RecordHistory(newHistory))

        // Then
        assertEquals(1, historiesSizeBeforeAdd)

        val finalState = middleware.businessState.value
        assertEquals(2, finalState.histories.size)
    }

    @Test
    fun `RecordHistory intent with duplicate history should not modify state`() =
        runTest(testDispatcher) {
            // Given
            val history = createMockHistory(id = "duplicate-id", name = "duplicate-repo")

            // When - Add the same history twice
            middleware.conveyIntention(AppIntent.RecordHistory(history))
            val stateAfterFirstAdd = middleware.businessState.value

            middleware.conveyIntention(AppIntent.RecordHistory(history))
            val stateAfterSecondAdd = middleware.businessState.value

            // Then
            assertEquals(1, stateAfterFirstAdd.histories.size)
            assertEquals(1, stateAfterSecondAdd.histories.size)
            assertEquals(stateAfterFirstAdd.histories, stateAfterSecondAdd.histories)
        }

    @Test
    fun `RecordHistory intent with duplicate history without datetime should not modify state`() =
        runTest(testDispatcher) {
            // Given
            val targetName = "duplicate-repo"
            val firstHistory = createMockHistory(
                id = "duplicate-id",
                targetName,
                dateTime = LocalDateTime.of(2025, 3, 15, 13, 49, 45),
            )
            val secondHistory = createMockHistory(
                id = "duplicate-id2",
                targetName,
                dateTime = LocalDateTime.of(2025, 3, 15, 13, 49, 50),
            )

            // When
            middleware.conveyIntention(AppIntent.RecordHistory(firstHistory))
            val stateAfterFirstAdd = middleware.businessState.value

            middleware.conveyIntention(AppIntent.RecordHistory(secondHistory))
            val stateAfterSecondAdd = middleware.businessState.value

            // Then
            assertEquals(1, stateAfterFirstAdd.histories.size)
            assertEquals(1, stateAfterSecondAdd.histories.size)
            assertFalse(stateAfterFirstAdd.histories == stateAfterSecondAdd.histories)

            val stateAfterSecondAddTargetHistory =
                stateAfterSecondAdd.histories.find { it.openedSearchedRepository.name == targetName }
            assertEquals(stateAfterSecondAddTargetHistory, secondHistory)
        }

    companion object {
        private fun createMockHistory(
            id: String = "history-id",
            name: String = "repository-name",
            dateTime: LocalDateTime = LocalDateTime.now(),
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
                    openIssuesCount = 50L,
                ),
            )
        }
    }
}
