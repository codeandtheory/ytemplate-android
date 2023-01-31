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
import ytemplate.android.database.model.Post
import ytemplate.android.database.model.PostDao

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    private lateinit var myModelDao: PostDao
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
        val myModel = Post(1,2,"title","body")
        myModelDao.insert(myModel)
        val data = myModelDao.getAllPost().first().first()
        Assert.assertEquals(myModel.id, data.id)
    }

    @After
    @kotlin.jvm.Throws(Exception::class)
    fun tearDown() {
        database.close()
    }
}