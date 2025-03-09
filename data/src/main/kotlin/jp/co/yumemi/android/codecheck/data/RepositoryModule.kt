package jp.co.yumemi.android.codecheck.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideGithubRepository(): GithubRepository {
        return GithubRepositoryImpl()
    }
}
