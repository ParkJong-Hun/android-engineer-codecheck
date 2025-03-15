package jp.co.yumemi.android.codecheck.feature.history

import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiEvent

sealed interface SearchHistoryUiEvent : UiEvent {
    data class OnClickHistory(val history: History) : SearchHistoryUiEvent
}
