package ytemplate.android.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ytemplate.android.core.database.dao.PostDao
import ytemplate.android.core.database.model.PostEntity
import ytemplate.android.core.database.room.AppDataBase
import kotlin.test.assertTrue

/**
 * App data base test
 *
 * @constructor Create empty App data base test
 */
@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    private lateinit var myModelDao: PostDao
    private lateinit var database: AppDataBase

    /**
     * Set up
     *
     */
    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java).build()
        myModelDao = database.myModelDao()
    }

    /**
     * Test write my model
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testWriteMyModel() = runTest {
        val myModel = PostEntity(1, 2, "title", "body")
        myModelDao.insert(myModel)
        val data = myModelDao.getAllPost().first()
        Assert.assertEquals(myModel.id, data.id)
    }

    /**
     * Test get Model
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testGetAllModels() = runTest {
        val myModel = PostEntity(1, 2, "title", "body")
        myModelDao.insert(myModel)
        val data = myModelDao.getAllPost().first()
        Assert.assertEquals(myModel.id, data.id)
    }

    /**
     * Test delete Model
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testDeleteModel() = runTest {
        val myModel = PostEntity(3, 3, "title3", "body3")
        myModelDao.deleteAll() // clear database
        myModelDao.insert(myModel) // add one item
        val data = myModelDao.getAllPost().first()
        Assert.assertEquals(myModel.id, data.id)
        myModelDao.deleteItem(myModel.id) // delete single item
        val updatedData = myModelDao.getAllPost()
        assertTrue(updatedData.isEmpty()) // check its empty
    }

    /**
     * Test delete all Models
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testDeleteAllModels() = runTest {
        val myModel = PostEntity(3, 3, "title3", "body3")
        myModelDao.insert(myModel)
        val data = myModelDao.getAllPost().first()
        Assert.assertEquals(myModel.id, data.id)
        myModelDao.deleteAll()
        val updated = myModelDao.getAllPost()
        Assert.assertTrue(updated.isEmpty())
    }

    /**
     * Tear down
     *
     */
    @After
    @kotlin.jvm.Throws(Exception::class)
    fun tearDown() {
        database.close()
    }
}
