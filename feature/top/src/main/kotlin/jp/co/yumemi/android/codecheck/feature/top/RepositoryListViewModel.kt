package jp.co.yumemi.android.codecheck.feature.top

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import javax.inject.Inject

/**
 * [RepositoryListFragment]のViewModel。
 */
@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    fun getSearchedRepositoryItemInfo(inputText: String): List<SearchedRepositoryItemInfo> =
        githubRepository.getSearchedRepositoryItemInfo(inputText)
}
