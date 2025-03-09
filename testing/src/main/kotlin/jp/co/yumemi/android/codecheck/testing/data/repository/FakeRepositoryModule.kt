package jp.co.yumemi.android.codecheck.testing.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import jp.co.yumemi.android.codecheck.data.repository.RepositoryModule
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
@Module
object FakeRepositoryModule {
    @Singleton
    @Provides
    fun provideGithubRepository(): GithubRepository {
        return FakeGithubRepository()
    }
}
