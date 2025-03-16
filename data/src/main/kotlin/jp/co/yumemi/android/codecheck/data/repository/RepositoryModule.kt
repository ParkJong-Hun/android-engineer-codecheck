package jp.co.yumemi.android.codecheck.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.data.IoDispatcher
import jp.co.yumemi.android.codecheck.data.datasource.GithubApiDataSource
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGithubRepository(
        githubApiDataSource: GithubApiDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): GithubRepository {
        return GithubRepositoryImpl(githubApiDataSource, ioDispatcher)
    }
}
