package jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UiEvent

sealed interface RepositoryDetailUiEvent : UiEvent {
    data class OnRepositoryDetailCreate(val repository: SearchedRepository) :
        RepositoryDetailUiEvent
}
