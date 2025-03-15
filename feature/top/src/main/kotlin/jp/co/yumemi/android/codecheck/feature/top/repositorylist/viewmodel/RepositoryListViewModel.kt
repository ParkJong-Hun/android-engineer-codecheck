package jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryIntent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RepositoryListFragment]のViewModel。
 */
@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val searchRepositoryMiddleware: Middleware<SearchRepositoryState, SearchRepositoryIntent>
) : ViewModel() {

    private val _uiState = MutableStateFlow<RepositoryListUiState>(RepositoryListUiState.None)
    val uiState: StateFlow<RepositoryListUiState> = _uiState

    init {
        collectBusinessState()
    }

    fun onSearchClick(inputString: String) {
        if (inputString.isEmpty()) return
        viewModelScope.launch {
            searchRepositoryMiddleware.conveyIntention(SearchRepositoryIntent.Search(inputString))
        }
    }

    private fun collectBusinessState() {
        viewModelScope.launch {
            searchRepositoryMiddleware.businessState.collect { state ->
                when (state) {
                    is SearchRepositoryState.None -> {
                        _uiState.value = RepositoryListUiState.None
                    }

                    is SearchRepositoryState.Loading -> {
                        _uiState.value = RepositoryListUiState.Loading
                    }

                    is SearchRepositoryState.Stable -> {
                        if (state.items.isEmpty()) {
                            _uiState.value = RepositoryListUiState.Empty
                        } else {
                            _uiState.value = RepositoryListUiState.Success(state.items)
                        }
                    }

                    is SearchRepositoryState.Error -> {
                        _uiState.value = RepositoryListUiState.Error(state.message)
                    }
                }
            }
        }
    }
}
