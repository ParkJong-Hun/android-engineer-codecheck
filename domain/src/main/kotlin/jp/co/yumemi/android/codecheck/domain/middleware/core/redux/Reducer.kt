package jp.co.yumemi.android.codecheck.domain.middleware.core.redux

import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState

internal interface Reducer<S : BusinessState, I : BusinessIntent> {
    fun reduce(currentState: S, intent: I): S
}
