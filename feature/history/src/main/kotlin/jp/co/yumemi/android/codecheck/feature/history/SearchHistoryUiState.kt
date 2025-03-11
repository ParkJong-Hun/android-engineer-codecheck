package jp.co.yumemi.android.codecheck.feature.history

import jp.co.yumemi.android.codecheck.domain.entity.Histories

sealed interface SearchHistoryUiState {
    data class Idle(val histories: Histories) : SearchHistoryUiState
    data object Empty : SearchHistoryUiState
}
