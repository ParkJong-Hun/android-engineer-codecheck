package jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiState

sealed interface RepositoryDetailUiState : UiState {
    data object None : RepositoryDetailUiState
    data class Success(val repository: SearchedRepository) : RepositoryDetailUiState
}
