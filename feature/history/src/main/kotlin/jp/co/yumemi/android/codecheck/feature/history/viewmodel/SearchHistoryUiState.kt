package jp.co.yumemi.android.codecheck.feature.history.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiState

sealed interface SearchHistoryUiState : UiState {
    data class Idle(
        val histories: List<History>,
        val onClickedHistory: Pair<History?, Boolean> = null to false,
    ) : SearchHistoryUiState

    data object Empty : SearchHistoryUiState
}
