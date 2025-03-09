package jp.co.yumemi.android.codecheck.feature.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [RepositoryListFragment]のViewModel。
 */
@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RepositoryListUiState>(RepositoryListUiState.Empty)
    val uiState: StateFlow<RepositoryListUiState> = _uiState

    fun searchRepositories(query: String) {
        if (query.isEmpty()) return

        viewModelScope.launch {
            _uiState.value = RepositoryListUiState.Loading

            runCatching {
                githubRepository.getSearchedRepositoryItemInfo(query)
            }.onSuccess { results ->
                if (results.isEmpty()) {
                    _uiState.value = RepositoryListUiState.Empty
                } else {
                    _uiState.value = RepositoryListUiState.Success(results)
                }
            }.onFailure { error ->
                _uiState.value =
                    RepositoryListUiState.Error(error.message ?: "Unknown error occurred")
            }
        }
    }
}
