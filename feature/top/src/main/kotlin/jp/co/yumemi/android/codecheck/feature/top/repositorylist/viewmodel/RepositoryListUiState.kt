package jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiState

sealed interface RepositoryListUiState : UiState {
    data object None : RepositoryListUiState
    data object Loading : RepositoryListUiState
    data class Error(val message: String) : RepositoryListUiState

    sealed interface Stable : RepositoryListUiState {
        val onClickedGoHistory: Boolean

        data class Success(
            val onClickedSearchedRepository: Pair<SearchedRepository?, Boolean> = null to false,
            override val onClickedGoHistory: Boolean = false,
            val repositories: List<SearchedRepository>
        ) : Stable

        data class Empty(
            override val onClickedGoHistory: Boolean = false
        ) : Stable
    }
}
