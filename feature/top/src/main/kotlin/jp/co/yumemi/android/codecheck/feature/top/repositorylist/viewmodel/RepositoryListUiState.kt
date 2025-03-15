package jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository

sealed class RepositoryListUiState {
    data object None : RepositoryListUiState()
    data object Loading : RepositoryListUiState()
    data class Success(val repositories: List<SearchedRepository>) : RepositoryListUiState()
    data class Error(val message: String) : RepositoryListUiState()
    data object Empty : RepositoryListUiState()
}
