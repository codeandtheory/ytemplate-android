package ytemplate.android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ytemplate.android.database.model.MyModel
import ytemplate.android.database.model.MyModelDao
import javax.inject.Inject

interface MyModelRepository {

    val myModel: Flow<List<String>>
    suspend fun add(name: String)
}

class MyModelRepositoryImpl @Inject constructor(private val myModelDao: MyModelDao) :
    MyModelRepository {
    override val myModel: Flow<List<String>>
        get() = myModelDao.getMyModels().map { items-> items.map { it.name } }

    override suspend fun add(name: String) {
       myModelDao.insert(MyModel(name = name))
    }

}