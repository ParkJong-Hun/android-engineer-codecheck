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

fun History.getFormattedDetails(): String {
    return buildString {
        if (openedSearchedRepository.language.isNotEmpty()) {
            append(openedSearchedRepository.language)
        }

        if (openedSearchedRepository.stargazersCount > 0) {
            if (this.isNotEmpty()) append(" • ")
            append("★ ")
            if (openedSearchedRepository.stargazersCount < 1000) {
                append(openedSearchedRepository.stargazersCount)
            } else {
                val formatted =
                    "%.1f".format(openedSearchedRepository.stargazersCount / 1000.0)
                append("${formatted}k")
            }
        }
    }
}
