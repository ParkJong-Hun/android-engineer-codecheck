package jp.co.yumemi.android.codecheck.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val appStateMiddleware: Middleware<AppState, AppIntent>,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchHistoryUiState>(SearchHistoryUiState.Empty)
    val uiState = _uiState.asStateFlow()

    val uiEvent = MutableSharedFlow<SearchHistoryUiEvent>(
        replay = 1,
        extraBufferCapacity = 10,
    )

    init {
        collectUiEvent()
        collectAppStateMiddleware()
    }

    private fun collectUiEvent() {
        viewModelScope.launch {
            uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    is SearchHistoryUiEvent.OnClickHistory -> {
                        if (uiState.value is SearchHistoryUiState.Idle) {
                            _uiState.update {
                                (it as SearchHistoryUiState.Idle).copy(
                                    onClickedHistory = uiEvent.history to true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun collectAppStateMiddleware() {
        viewModelScope.launch {
            appStateMiddleware.businessState.collect {
                _uiState.value = if (it.histories.isNotEmpty()) {
                    SearchHistoryUiState.Idle(it.histories.toList())
                } else {
                    SearchHistoryUiState.Empty
                }
            }
        }
    }
}
