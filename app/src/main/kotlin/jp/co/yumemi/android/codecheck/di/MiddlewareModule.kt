package jp.co.yumemi.android.codecheck.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryIntent
import jp.co.yumemi.android.codecheck.domain.middleware.SearchRepositoryState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.domain.middleware.searchRepositoryMiddleware
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository

@InstallIn(ViewModelComponent::class)
@Module
object MiddlewareModule {
    @Provides
    fun provideSearchRepositoryMiddleware(
        githubRepository: GithubRepository,
    ): Middleware<SearchRepositoryState, SearchRepositoryIntent> {
        return searchRepositoryMiddleware(
            initialState = SearchRepositoryState.None,
            githubRepository = githubRepository
        )
    }
}
