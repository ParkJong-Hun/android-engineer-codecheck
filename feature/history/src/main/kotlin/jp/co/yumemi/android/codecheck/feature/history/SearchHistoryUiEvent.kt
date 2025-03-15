package jp.co.yumemi.android.codecheck.feature.history

import jp.co.yumemi.android.codecheck.domain.entity.History

sealed interface SearchHistoryUiEvent {
    data class OnClickHistory(val history: History) : SearchHistoryUiEvent
}
