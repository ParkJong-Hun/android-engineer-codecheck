package jp.co.yumemi.android.codecheck.domain.middleware

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessIntent
import jp.co.yumemi.android.codecheck.domain.middleware.core.BusinessState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Reducer
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.SideEffectHandler
import jp.co.yumemi.android.codecheck.domain.middleware.core.redux.Store
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository

sealed class SearchRepositoryState : BusinessState {
    data object None : SearchRepositoryState()
    data object Loading : SearchRepositoryState()
    data class Stable(val items: List<SearchedRepository>) : SearchRepositoryState()
    data class Error(val message: String) : SearchRepositoryState()
}

sealed class SearchRepositoryIntent : BusinessIntent {
    data class Search(val query: String) : SearchRepositoryIntent()
    internal data class SearchComplete(val searchedItems: List<SearchedRepository>) :
        SearchRepositoryIntent()

    internal data class Failure(val throwable: Throwable) : SearchRepositoryIntent()
}

internal class SearchRepositoryReducer : Reducer<SearchRepositoryState, SearchRepositoryIntent> {
    override fun reduce(
        currentState: SearchRepositoryState,
        intent: SearchRepositoryIntent,
    ): SearchRepositoryState {
        return when (intent) {
            is SearchRepositoryIntent.Search -> {
                SearchRepositoryState.Loading
            }

            is SearchRepositoryIntent.SearchComplete -> {
                SearchRepositoryState.Stable(intent.searchedItems)
            }

            is SearchRepositoryIntent.Failure -> {
                SearchRepositoryState.Error(intent.throwable.message ?: "Unknown error")
            }
        }
    }
}

internal class SearchRepositorySideEffectHandler(
    private val githubRepository: GithubRepository,
) : SideEffectHandler<SearchRepositoryState, SearchRepositoryIntent> {
    override suspend fun handle(
        state: SearchRepositoryState,
        intent: SearchRepositoryIntent,
        conveyIntention: (SearchRepositoryIntent) -> Unit,
    ) {
        when (intent) {
            is SearchRepositoryIntent.Search -> {
                when (state) {
                    is SearchRepositoryState.Loading -> {
                        runCatching {
                            githubRepository.getSearchedRepositoryItemInfo(intent.query)
                        }.onSuccess {
                            conveyIntention(SearchRepositoryIntent.SearchComplete(it))
                        }.onFailure {
                            conveyIntention(SearchRepositoryIntent.Failure(it))
                        }
                    }

                    else -> Unit
                }
            }

            else -> Unit
        }
    }
}

internal class SearchRepositoryStore(
    initialState: SearchRepositoryState,
    reducer: Reducer<SearchRepositoryState, SearchRepositoryIntent>,
    sideEffectHandlers: List<SideEffectHandler<SearchRepositoryState, SearchRepositoryIntent>> = emptyList(),
) : Store<SearchRepositoryState, SearchRepositoryIntent>(
    initialState,
    reducer,
    sideEffectHandlers,
)

fun searchRepositoryMiddleware(
    initialState: SearchRepositoryState,
    githubRepository: GithubRepository,
): Middleware<SearchRepositoryState, SearchRepositoryIntent> = SearchRepositoryStore(
    initialState,
    SearchRepositoryReducer(),
    listOf(SearchRepositorySideEffectHandler(githubRepository)),
)
