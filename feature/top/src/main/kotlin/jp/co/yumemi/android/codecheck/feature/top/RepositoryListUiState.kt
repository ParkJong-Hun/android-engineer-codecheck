package jp.co.yumemi.android.codecheck.feature.top

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo

sealed class RepositoryListUiState {
    data object Loading : RepositoryListUiState()
    data class Success(val repositories: List<SearchedRepositoryItemInfo>) : RepositoryListUiState()
    data class Error(val message: String) : RepositoryListUiState()
    data object Empty : RepositoryListUiState()
}
