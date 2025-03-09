package jp.co.yumemi.android.codecheck.domain.middleware.redux

import jp.co.yumemi.android.codecheck.domain.middleware.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.BusinessState

interface SideEffectHandler<S : BusinessState, I : BusinessIntent> {
    suspend fun handle(state: S, intent: I, conveyIntention: (I) -> Unit)
}
