package jp.co.yumemi.android.codecheck.presentation.uiarchitecture

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UdfViewModelInterface<S : UiState, I : UiEvent> {
    val uiState: StateFlow<S>
    val uiEvent: MutableSharedFlow<I>
}
