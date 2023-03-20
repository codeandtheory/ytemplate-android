package ytemplate.android.network

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ytemplate.android.core.network.apis.PostApiImpl
import ytemplate.android.core.network.model.PostDTO
import ytemplate.android.core.network.model.PostRequest
import ytemplate.android.core.test.PlaceHolderData
import ytemplate.android.core.test.RemotePostDataMock

/**
 * Remote repository test
 *
 * @constructor Create empty Remote repository test
 */
class RemoteRepositoryTest {
    private val remotePostDataMock = RemotePostDataMock()

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
        remotePostDataMock.setExpectSuccess()
        remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val repository = PostApiImpl(remotePostDataMock.httpClient)
        val data = repository.get()
        assert(data is List<PostDTO>)
    }

    /**
     * put a post test
     *
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `put post test`() = runBlocking {
        remotePostDataMock.setExpectSuccess()
        remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val repository = PostApiImpl(remotePostDataMock.httpClient)
        val data = repository.post(PostRequest("title", "body", 1))
        assert(!data)
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
