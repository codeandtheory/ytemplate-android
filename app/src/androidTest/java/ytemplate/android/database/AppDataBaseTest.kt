package ytemplate.android.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ytemplate.android.database.model.MyModel
import ytemplate.android.database.model.MyModelDao

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    private lateinit var myModelDao: MyModelDao
    private lateinit var database: AppDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        myModelDao = database.myModelDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testWriteMyModel() = runTest {
        val myModel = MyModel(name = "Test")
        myModelDao.insert(myModel)
        val data = myModelDao.getMyModels().first().first()
        Assert.assertEquals(myModel.name, data.name)
    }

    @After
    @kotlin.jvm.Throws(Exception::class)
    fun tearDown() {
        database.close()
    }
}