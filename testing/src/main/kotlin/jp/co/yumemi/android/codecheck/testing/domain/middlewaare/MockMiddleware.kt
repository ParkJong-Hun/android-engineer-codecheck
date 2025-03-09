package jp.co.yumemi.android.codecheck.testing.domain.middlewaare

import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockMiddleware<S : BusinessState, I : BusinessIntent>(
    initialState: S,
) : Middleware<S, I> {
    val updatableBusinessState = MutableStateFlow(initialState)
    override val businessState: StateFlow<S> = updatableBusinessState.asStateFlow()
    override fun conveyIntention(intent: I) {
        // Mock implementation
    }
}
