package jp.co.yumemi.android.codecheck.domain.middleware.core.redux

import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState

internal interface SideEffectHandler<S : BusinessState, I : BusinessIntent> {
    suspend fun handle(state: S, intent: I, conveyIntention: (I) -> Unit)
}
