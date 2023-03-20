package ytemplate.android.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Dispatcher module
 *
 * @constructor Create empty Dispatcher module
 */
@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    /**
     * Provide network dispatcher
     *
     * @return
     */
    @Provides
    fun provideNetworkDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
