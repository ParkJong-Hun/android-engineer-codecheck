@file:Suppress("ForbiddenComment", "UnusedPrivateProperty")

package jp.co.yumemi.android.codecheck.feature.top

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import io.mockk.spyk
import jp.co.yumemi.android.codecheck.domain.entity.Histories
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.presentation.time.NowProvider
import jp.co.yumemi.android.codecheck.testing.domain.middlewaare.MockMiddleware
import jp.co.yumemi.android.codecheck.testing.presentation.time.FakeNowProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RepositoryDetailViewModel
    private lateinit var appMiddleware: MockMiddleware<AppState, AppIntent>
    private val nowProvider: NowProvider = FakeNowProvider()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val emptyHistories = Histories(emptyList())
        appMiddleware = spyk(MockMiddleware(AppState(histories = emptyHistories)))
        viewModel = RepositoryDetailViewModel(appMiddleware, nowProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onRepositoryDetailCreate records history with correct repository`() {
        // Given
        val repository = MOCK_REPOSITORY

        val intentSlot = slot<AppIntent.RecordHistory>()
        coEvery { appMiddleware.conveyIntention(capture(intentSlot)) } answers { }

        // When
        viewModel.onRepositoryDetailCreate(repository)

        // Then
        coVerify { appMiddleware.conveyIntention(any<AppIntent.RecordHistory>()) }

        val capturedIntent = intentSlot.captured
        val history = capturedIntent.history

        assertEquals(FakeNowProvider.fakeLocalDateTimeNow, history.openedDateTime)
        assertEquals(repository, history.openedSearchedRepository)
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
