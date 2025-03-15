package jp.co.yumemi.android.codecheck

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.feature.history.router.HistoryRouter
import jp.co.yumemi.android.codecheck.feature.top.router.TopRouter
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RouterModule {
    @Singleton
    @Provides
    fun provideTopRouter(): TopRouter {
        return TopRouterImpl()
    }

    @Singleton
    @Provides
    fun provideHistoryRouter(): HistoryRouter {
        return HistoryRouterImpl()
    }
}
