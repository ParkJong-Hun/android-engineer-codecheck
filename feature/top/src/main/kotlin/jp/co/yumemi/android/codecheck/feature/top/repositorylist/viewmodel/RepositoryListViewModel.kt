package jp.co.yumemi.android.codecheck.feature.top.repositorylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryIntent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.presentation.uiarchitecture.UdfViewModelInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RepositoryListFragment]のViewModel。
 */
@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val searchRepositoryMiddleware: Middleware<SearchRepositoryState, SearchRepositoryIntent>
) : ViewModel(), UdfViewModelInterface<RepositoryListUiState, RepositoryListUiEvent> {

    private val _uiState = MutableStateFlow<RepositoryListUiState>(RepositoryListUiState.None)
    override val uiState: StateFlow<RepositoryListUiState> = _uiState

    override val uiEvent = MutableSharedFlow<RepositoryListUiEvent>(
        replay = 1,
        extraBufferCapacity = 10,
    )

    init {
        collectBusinessState()
        collectUiEvent()
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
                            _uiState.value = RepositoryListUiState.Stable.Empty()
                        } else {
                            _uiState.value =
                                RepositoryListUiState.Stable.Success(repositories = state.items)
                        }
                    }

                    is SearchRepositoryState.Error -> {
                        _uiState.value = RepositoryListUiState.Error(state.message)
                    }
                }
            }
        }
    }

    private fun collectUiEvent() {
        viewModelScope.launch {
            uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    is RepositoryListUiEvent.OnSearchClick -> {
                        val query = uiEvent.inputString.toString().trim()
                        if (query.isNotEmpty()) {
                            searchRepositoryMiddleware.conveyIntention(
                                SearchRepositoryIntent.Search(query)
                            )
                        }
                    }

                    is RepositoryListUiEvent.OnClickSearchedRepository -> {
                        if (uiState.value is RepositoryListUiState.Stable.Success) {
                            _uiState.update {
                                (it as RepositoryListUiState.Stable.Success).copy(
                                    onClickedSearchedRepository = uiEvent.searchedRepository to true
                                )
                            }
                        }
                    }

                    is RepositoryListUiEvent.FinishNavigateToRepositoryDetail -> {
                        if (uiState.value is RepositoryListUiState.Stable.Success) {
                            _uiState.update {
                                (it as RepositoryListUiState.Stable.Success).copy(
                                    onClickedSearchedRepository = null to false
                                )
                            }
                        }
                    }

                    is RepositoryListUiEvent.OnClickGoHistory -> {
                        when (uiState.value) {
                            is RepositoryListUiState.Stable.Success -> {
                                _uiState.update {
                                    (it as RepositoryListUiState.Stable.Success).copy(
                                        onClickedGoHistory = true
                                    )
                                }
                            }

                            is RepositoryListUiState.Stable.Empty -> {
                                _uiState.update {
                                    (it as RepositoryListUiState.Stable.Empty).copy(
                                        onClickedGoHistory = true
                                    )
                                }
                            }

                            else -> {
                                // no-op
                            }
                        }
                    }

                    is RepositoryListUiEvent.FinishNavigateToSearchHistory -> {
                        _uiState.update {
                            (it as RepositoryListUiState.Stable.Success).copy(
                                onClickedGoHistory = false
                            )
                        }
                    }
                }
            }
        }
    }
}
