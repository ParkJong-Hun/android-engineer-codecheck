package jp.co.yumemi.android.codecheck.domain.middleware.redux

import jp.co.yumemi.android.codecheck.domain.middleware.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.BusinessState

internal interface Reducer<S : BusinessState, I : BusinessIntent> {
    fun reduce(currentState: S, intent: I): S
}
