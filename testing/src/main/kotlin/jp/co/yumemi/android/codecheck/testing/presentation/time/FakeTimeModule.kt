package jp.co.yumemi.android.codecheck.testing.presentation.time

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import jp.co.yumemi.android.codecheck.presentation.time.NowProvider
import jp.co.yumemi.android.codecheck.presentation.time.TimeModule
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TimeModule::class],
)
@Module
object FakeTimeModule {
    @Provides
    @Singleton
    fun provideNowProvider(): NowProvider = FakeNowProvider()
}
