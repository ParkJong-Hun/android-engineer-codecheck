package jp.co.yumemi.android.codecheck.domain.middleware.redux

import jp.co.yumemi.android.codecheck.domain.middleware.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.BusinessState
import jp.co.yumemi.android.codecheck.domain.middleware.Middleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class Store<S : BusinessState, I : BusinessIntent>(
    initialState: S,
    private val reducer: Reducer<S, I>,
    private val sideEffectHandlers: List<SideEffectHandler<S, I>> = emptyList()
) : Middleware<S, I> {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _businessState = MutableStateFlow(initialState)
    override val businessState: StateFlow<S> = _businessState.asStateFlow()

    override fun conveyIntention(intent: I) {
        val newState = reducer.reduce(_businessState.value, intent)
        handleSideEffects(intent)
        _businessState.value = newState
    }

    private fun handleSideEffects(intent: I) {
        sideEffectHandlers.forEach { effectHandler ->
            coroutineScope.launch {
                effectHandler.handle(businessState.value, intent) { intentFromSideEffect ->
                    conveyIntention(intentFromSideEffect)
                }
            }
        }
    }
}
