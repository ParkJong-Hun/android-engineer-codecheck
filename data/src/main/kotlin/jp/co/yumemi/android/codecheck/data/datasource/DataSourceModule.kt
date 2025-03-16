package jp.co.yumemi.android.codecheck.data.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jp.co.yumemi.android.codecheck.data.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideGithubApiDataSource(
        httpClient: HttpClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): GithubApiDataSource {
        return GithubApiDataSource(httpClient, ioDispatcher)
    }
}
