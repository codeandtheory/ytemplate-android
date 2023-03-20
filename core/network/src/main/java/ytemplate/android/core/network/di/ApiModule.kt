package ytemplate.android.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ytemplate.android.core.network.apis.PostApi
import ytemplate.android.core.network.apis.PostApiImpl

/**
 * ApiModule, will bind the repository and data source instance based on the demand
 *
 * @constructor Create empty Api module
 */
@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {
    /**
     * Bind post api
     *
     * @param postApi
     * @return
     */
    @Binds
    fun bindPostApi(postApi: PostApiImpl): PostApi
}
