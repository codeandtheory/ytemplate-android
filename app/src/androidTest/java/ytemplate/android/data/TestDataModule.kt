package ytemplate.android.data

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ytemplate.android.data.di.DataModule
import ytemplate.android.data.di.FakeMyModelRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {
    @Binds
    fun bindMyModelRepository(fakeMyModelRepository: FakeMyModelRepository): MyModelRepository
}