package ytemplate.android.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.data.PostRepositoryImpl
import ytemplate.android.core.data.local.LocalPostDataSourceImpl
import ytemplate.android.core.data.remote.RemotePostDataSourceImpl
import ytemplate.android.core.test.FakePostApi
import ytemplate.android.core.test.FakePostDao
import ytemplate.android.core.test.PlaceHolderData
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Post repository impl test
 *
 * @constructor Create empty Post repository impl test
 */
class PostRepositoryImplTest {

    private val fakePostApi = FakePostApi()
    private val fakePostDao = FakePostDao()
    private val repository = PostRepositoryImpl(
        dispatcher = UnconfinedTestDispatcher(),
        remotePostDataSource = RemotePostDataSourceImpl(fakePostApi),
        localPostDataSource = LocalPostDataSourceImpl(fakePostDao)
    )

    /**
     * Set up
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    /**
     * Get all post test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get all post test`() = runBlocking {
        fakePostApi.remotePostDataMock.setExpectSuccess()
        fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val data = repository.loadInitialData()
        assert(data is AppResult.Success)
    }

    /**
     * Get all post test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get all post test failure scenario`() = runBlocking {
        fakePostApi.remotePostDataMock.setExpectFailure()
        fakePostApi.remotePostDataMock.setExpectedResponse(null)
        val data = repository.loadInitialData()
        assert(data is AppResult.Error)
    }

    /**
     * feed flow test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `feed flow test`() = runBlocking {
        fakePostApi.remotePostDataMock.setExpectSuccess()
        fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val values: MutableList<List<Post>> = ArrayList()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            repository.feed.toList(values)
        }
        repository.loadInitialData() // load initial data which emits fake data supplied
        assertTrue(values.first()[0].id == 1) // check if the returned data is same as fake supplied data
        collectJob.cancel()
    }

    /**
     * delete post item test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `delete post item test`() = runBlocking {
        fakePostApi.remotePostDataMock.setExpectSuccess()
        fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val values: MutableList<List<Post>> = ArrayList()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            repository.feed.toList(values)
        }
        repository.loadInitialData() // load initial data which emits fake data supplied
        assertTrue(values.first()[0].id == 1) // check if the returned data is same as fake supplied data
        val deletable = values.first()[0]
        repository.delete(deletable)
        assertTrue(values[1][0].id != 1) // check if deleted value present in the flow or not
        collectJob.cancel()
    }

    /**
     * add post item test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `add post item test`() = runBlocking {
        fakePostApi.remotePostDataMock.setExpectSuccess()
        fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val values: MutableList<List<Post>> = ArrayList()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            repository.feed.toList(values)
        }
        val dummyPost = Post(111, 111, "dummy title", "dummy body")
        repository.add(dummyPost) // load initial data which emits fake data supplied
        assertEquals(values.first()[0].id, dummyPost.id) // check if the returned data is same as supplied dummy data
        collectJob.cancel()
    }

    /**
     * Tear down
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
