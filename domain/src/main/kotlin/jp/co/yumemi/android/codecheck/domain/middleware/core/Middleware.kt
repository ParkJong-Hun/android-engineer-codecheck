package jp.co.yumemi.android.codecheck.domain.middleware.core

import kotlinx.coroutines.flow.StateFlow

/**
 * 状態と意図を管理する主体。
 */
interface Middleware<S : BusinessState, I : BusinessIntent> {
    val businessState: StateFlow<S>
    fun conveyIntention(intent: I)
}
