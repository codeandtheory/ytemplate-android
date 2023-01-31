package ytemplate.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ytemplate.android.data.MyPostRepository
import ytemplate.android.data.MyPostRepositoryImpl
import ytemplate.android.data.datasource.LocalPostDataSource
import ytemplate.android.data.datasource.LocalPostDataSourceImpl
import ytemplate.android.data.datasource.RemotePostDataSource
import ytemplate.android.data.datasource.RemotePostDataSourceImpl

/**
 * Repository module, will bind the repository and data source instance based on the demand
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMyModelRepository(myModelRepository: MyPostRepositoryImpl): MyPostRepository
    @Binds
    fun bindLocalPostDataSource(localPostDataSource: LocalPostDataSourceImpl): LocalPostDataSource

    @Binds
    fun bindRemotePostDataSource(remotePostDataSource: RemotePostDataSourceImpl): RemotePostDataSource

}
