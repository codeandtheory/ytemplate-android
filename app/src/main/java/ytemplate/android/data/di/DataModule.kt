package ytemplate.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ytemplate.android.data.MyModelRepository
import ytemplate.android.data.MyModelRepositoryImpl
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindMyModelRepository(myModelRepository: MyModelRepositoryImpl): MyModelRepository
}

class FakeMyModelRepository @Inject constructor(): MyModelRepository {
    override val myModel: Flow<List<String>>
        get() = flowOf(fakeData)

    override suspend fun add(name: String) {
        TODO("Not yet implemented")
    }
}
val fakeData  = listOf("test1","test2","test3")