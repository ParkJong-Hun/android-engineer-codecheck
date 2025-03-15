package jp.co.yumemi.android.codecheck.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    appStateMiddleware: Middleware<AppState, AppIntent>,
) : ViewModel() {
    val uiState = appStateMiddleware.businessState
        .map {
            if (it.histories.isNotEmpty()) {
                SearchHistoryUiState.Idle(it.histories.toList())
            } else {
                SearchHistoryUiState.Empty
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SearchHistoryUiState.Empty,
        )
}
