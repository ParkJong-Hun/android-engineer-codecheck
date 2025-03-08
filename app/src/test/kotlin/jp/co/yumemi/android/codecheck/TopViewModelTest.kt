package jp.co.yumemi.android.codecheck

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
class TopViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TopViewModel

    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)

        val nowProvider: NowProvider = FakeNowProvider()
        viewModel = TopViewModel(nowProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testOnSearched() = runTest(testDispatcher) {
        assertEquals(null, viewModel.lastSearchDate.value)

        viewModel.onSearched()

        assertEquals(FakeNowProvider.fakeLocalDateNow, viewModel.lastSearchDate.value)
    }
}
