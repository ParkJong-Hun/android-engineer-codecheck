package jp.co.yumemi.android.codecheck.domain.middleware

import kotlinx.coroutines.flow.StateFlow

interface Middleware<S : BusinessState, I : BusinessIntent> {
    val businessState: StateFlow<S>
    fun conveyIntention(intent: I)
}
