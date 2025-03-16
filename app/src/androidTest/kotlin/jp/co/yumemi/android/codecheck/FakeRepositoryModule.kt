package jp.co.yumemi.android.codecheck

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import jp.co.yumemi.android.codecheck.data.repository.RepositoryModule
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModuleForTest {
    @Singleton
    @Provides
    fun provideGithubRepository(): GithubRepository {
        return object : GithubRepository {
            override suspend fun getSearchedRepositoryItemInfo(query: String): List<SearchedRepository> {
                if (query.contains("zxcvbnmasdfghjklqwertyuiop")) {
                    return emptyList()
                }

                return listOf(
                    SearchedRepository(
                        name = "jetbrains/kotlin",
                        ownerIconUrl = "https://avatars.githubusercontent.com/u/878437?v=4",
                        language = "Kotlin",
                        stargazersCount = 45000,
                        watchersCount = 1500,
                        forksCount = 5600,
                        openIssuesCount = 132
                    ),
                    SearchedRepository(
                        name = "android/architecture-components-samples",
                        ownerIconUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
                        language = "Kotlin",
                        stargazersCount = 21000,
                        watchersCount = 800,
                        forksCount = 6700,
                        openIssuesCount = 53
                    )
                )
            }
        }
    }
}
