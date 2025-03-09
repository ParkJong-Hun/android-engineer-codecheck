package jp.co.yumemi.android.codecheck.domain.middleware.core.redux

import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Store
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ReduxTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var sampleStore: Store<SampleState, SampleIntent>

    @Before
    fun setup() {
        sampleStore = Store(
            SampleState(),
            SampleReducer(),
            listOf(SampleSideEffectHandler())
        )
    }

    @Test
    fun `increment should increase count`() = runTest(testDispatcher) {
        assertEquals(0, sampleStore.businessState.value.count)

        sampleStore.conveyIntention(SampleIntent.Increment)

        assertEquals(1, sampleStore.businessState.value.count)
    }

    @Test
    fun `decrement should decrease count`() = runTest(testDispatcher) {
        sampleStore.conveyIntention(SampleIntent.AddValue(2))
        assertEquals(2, sampleStore.businessState.value.count)

        sampleStore.conveyIntention(SampleIntent.Decrement)

        assertEquals(1, sampleStore.businessState.value.count)
    }

    @Test
    fun `addValue should add the specified value`() = runTest(testDispatcher) {
        sampleStore.conveyIntention(SampleIntent.AddValue(4))

        assertEquals(4, sampleStore.businessState.value.count)
    }

    @Test
    fun `side effect handler should dispatch new intent when count is 5`() =
        runTest(testDispatcher) {
            sampleStore.conveyIntention(SampleIntent.AddValue(5))
            advanceUntilIdle()
            // it should dispatch AddValue(10) because of side effect.
            assertEquals(15, sampleStore.businessState.value.count)
        }
}
