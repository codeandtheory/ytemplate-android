package ytemplate.android.infra

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * AppModule, for maintaining app related DI to maintain
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Providing the network dispatcher.
     */
    @Provides
    fun provideNetworkDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}