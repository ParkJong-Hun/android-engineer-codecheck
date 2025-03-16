package jp.co.yumemi.android.codecheck.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.appMiddleware
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {
    @Singleton
    @Provides
    fun provideAppMiddleware(): Middleware<AppState, AppIntent> {
        return appMiddleware(AppState(histories = emptySet()))
    }
}
