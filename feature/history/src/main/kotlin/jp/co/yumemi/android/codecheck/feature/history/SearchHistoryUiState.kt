package jp.co.yumemi.android.codecheck.feature.history

import jp.co.yumemi.android.codecheck.domain.entity.History

sealed interface SearchHistoryUiState {
    data class Idle(val histories: List<History>) : SearchHistoryUiState
    data object Empty : SearchHistoryUiState
}
