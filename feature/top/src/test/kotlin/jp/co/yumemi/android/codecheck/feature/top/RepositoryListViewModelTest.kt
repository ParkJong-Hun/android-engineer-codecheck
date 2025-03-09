@file:Suppress("ForbiddenComment", "UnusedPrivateProperty")

package jp.co.yumemi.android.codecheck.feature.top

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// TODO: RepositoryListViewModelがテストできるようなコードになったらテストを書く。
class RepositoryListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RepositoryListViewModel

    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test() {
        assertEquals(true, true)
    }
}
