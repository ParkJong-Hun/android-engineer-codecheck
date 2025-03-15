package jp.co.yumemi.android.codecheck.feature.history

import jp.co.yumemi.android.codecheck.domain.entity.History

sealed interface SearchHistoryUiState {
    data class Idle(
        val histories: List<History>,
        val onClickedHistory: Pair<History?, Boolean> = null to false,
    ) : SearchHistoryUiState

    data object Empty : SearchHistoryUiState
}
