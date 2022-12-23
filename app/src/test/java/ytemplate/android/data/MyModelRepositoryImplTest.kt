package ytemplate.android.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ytemplate.android.database.model.MyModel
import ytemplate.android.database.model.MyModelDao

class MyModelRepositoryImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `add new item test`() = runTest {
        val repository = MyModelRepositoryImpl(FakeMyModelDao())
        repository.add("Test")
        assert(repository.myModel.first()[0] == "Test")

    }
}

private class FakeMyModelDao : MyModelDao {
    private val data = mutableListOf<MyModel>()
    override fun getMyModels(): Flow<List<MyModel>> = flow {
        emit(data)
    }

    override suspend fun insert(model: MyModel) {
        data.add(model)
    }

}