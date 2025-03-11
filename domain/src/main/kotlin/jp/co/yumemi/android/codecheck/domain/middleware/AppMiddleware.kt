package jp.co.yumemi.android.codecheck.domain.middleware

import jp.co.yumemi.android.codecheck.domain.entity.Histories
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Reducer
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Store

data class AppState(val histories: Histories) : BusinessState

sealed interface AppIntent : BusinessIntent {
    data class RecordHistory(val history: History) : AppIntent
}

internal class AppReducer : Reducer<AppState, AppIntent> {
    override fun reduce(currentState: AppState, intent: AppIntent): AppState {
        return when (intent) {
            is AppIntent.RecordHistory -> {
                currentState.copy(histories = Histories(currentState.histories.histories + intent.history))
            }
        }
    }
}

internal class AppStore(
    initialState: AppState,
    reducer: Reducer<AppState, AppIntent>,
) : Store<AppState, AppIntent>(
    initialState,
    reducer,
    listOf(),
)

fun appMiddleware(
    initialState: AppState,
): Middleware<AppState, AppIntent> = AppStore(
    initialState,
    AppReducer(),
)
