package jp.co.yumemi.android.codecheck.domain.middleware.redux

import jp.co.yumemi.android.codecheck.domain.middleware.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.BusinessState

internal data class SampleState(val count: Int = 0) : BusinessState

internal sealed class SampleIntent : BusinessIntent {
    data object Increment : SampleIntent()
    data object Decrement : SampleIntent()
    data class AddValue(val value: Int) : SampleIntent()
}

internal class SampleReducer : Reducer<SampleState, SampleIntent> {
    override fun reduce(currentState: SampleState, intent: SampleIntent): SampleState {
        return when (intent) {
            is SampleIntent.Increment -> currentState.copy(count = currentState.count + 1)
            is SampleIntent.Decrement -> currentState.copy(count = currentState.count - 1)
            is SampleIntent.AddValue -> currentState.copy(count = currentState.count + intent.value)
        }
    }
}

internal class SampleSideEffectHandler : SideEffectHandler<SampleState, SampleIntent> {
    override suspend fun handle(
        state: SampleState,
        intent: SampleIntent,
        conveyIntention: (SampleIntent) -> Unit
    ) {
        when (intent) {
            is SampleIntent.AddValue,
            is SampleIntent.Increment -> {
                if (state.count == 5) {
                    conveyIntention(SampleIntent.AddValue(10))
                }
            }

            else -> {
                // no op
            }
        }
    }
}
