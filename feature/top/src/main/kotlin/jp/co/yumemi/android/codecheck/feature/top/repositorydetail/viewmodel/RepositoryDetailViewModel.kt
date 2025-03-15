package jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.presentation.time.NowProvider
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UdfViewModelInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * Repository 상세 화면의 ViewModel
 */
@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val appMiddleware: Middleware<AppState, AppIntent>,
    private val nowProvider: NowProvider,
) : ViewModel(), UdfViewModelInterface<RepositoryDetailUiState, RepositoryDetailUiEvent> {

    private val _uiState = MutableStateFlow<RepositoryDetailUiState>(RepositoryDetailUiState.None)
    override val uiState: StateFlow<RepositoryDetailUiState> = _uiState

    override val uiEvent = MutableSharedFlow<RepositoryDetailUiEvent>(
        replay = 1,
        extraBufferCapacity = 10,
    )

    init {
        collectUiEvent()
    }

    private fun collectUiEvent() {
        viewModelScope.launch {
            uiEvent.collect { event ->
                when (event) {
                    is RepositoryDetailUiEvent.OnRepositoryDetailCreate -> {
                        recordHistory(event.repository)
                        _uiState.value = RepositoryDetailUiState.Success(event.repository)
                    }
                }
            }
        }
    }

    private fun recordHistory(repository: SearchedRepository) {
        val history = History(
            id = UUID.randomUUID().toString(),
            openedDateTime = nowProvider.localDateTimeNow(),
            openedSearchedRepository = repository,
        )
        appMiddleware.conveyIntention(AppIntent.RecordHistory(history))
    }
}
