@file:Suppress("ForbiddenComment")

package jp.co.yumemi.android.codecheck.domain.middleware

import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Reducer
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Store

data class AppState(
    // TODO: ImmutableListにする
    val histories: Set<History>
) : BusinessState

sealed interface AppIntent : BusinessIntent {
    data class RecordHistory(val history: History) : AppIntent
}

internal class AppReducer : Reducer<AppState, AppIntent> {
    override fun reduce(currentState: AppState, intent: AppIntent): AppState {
        return when (intent) {
            is AppIntent.RecordHistory -> {
                val existHistoryOrNull =
                    currentState.histories.find { it.openedSearchedRepository == intent.history.openedSearchedRepository }
                currentState.copy(
                    histories = if (existHistoryOrNull == null) {
                        currentState.histories + intent.history
                    } else {
                        currentState.histories.minus(existHistoryOrNull) + intent.history
                    }
                )
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
