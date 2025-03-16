package jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiEvent

sealed interface RepositoryListUiEvent : UiEvent {
    data class OnSearchClick(val inputString: CharSequence) : RepositoryListUiEvent
    data object OnClickGoHistory : RepositoryListUiEvent
    data class OnClickSearchedRepository(
        val searchedRepository: SearchedRepository,
    ) : RepositoryListUiEvent

    data object FinishNavigateToSearchHistory : RepositoryListUiEvent
    data object FinishNavigateToRepositoryDetail : RepositoryListUiEvent
}
