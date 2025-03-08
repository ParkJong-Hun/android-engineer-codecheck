package jp.co.yumemi.android.codecheck

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
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
