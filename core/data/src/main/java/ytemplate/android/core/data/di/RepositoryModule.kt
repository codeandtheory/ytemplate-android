package ytemplate.android.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ytemplate.android.core.data.PostRepository
import ytemplate.android.core.data.PostRepositoryImpl
import ytemplate.android.core.data.local.LocalPostDataSource
import ytemplate.android.core.data.local.LocalPostDataSourceImpl
import ytemplate.android.core.data.remote.RemotePostDataSource
import ytemplate.android.core.data.remote.RemotePostDataSourceImpl

/**
 * Repository module, will bind the repository and data source instance based on the demand
 *
 * @constructor Create empty Repository module
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    /**
     * Bind post repository
     *
     * @param postRepository
     * @return
     */
    @Binds
    fun bindPostRepository(postRepository: PostRepositoryImpl): PostRepository

    /**
     * Bind local post data source
     *
     * @param localPostDataSource
     * @return
     */
    @Binds
    fun bindLocalPostDataSource(localPostDataSource: LocalPostDataSourceImpl): LocalPostDataSource

    /**
     * Bind remote post data source
     *
     * @param remotePostDataSource
     * @return
     */
    @Binds
    fun bindRemotePostDataSource(remotePostDataSource: RemotePostDataSourceImpl): RemotePostDataSource
}
