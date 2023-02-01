package ytemplate.android.data

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
import ytemplate.android.data.datasource.LocalPostDataSourceImpl
import ytemplate.android.data.datasource.RemotePostDataSourceImpl
import ytemplate.android.infra.AppResult

class MyPostRepositoryImplTest {
    private val remotePostDataMock = RemotePostDataMock()
    private val fakePostDao = FakePostDao()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get all post test`() = runBlocking {
        remotePostDataMock.setExpectSuccess()
        remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val repository =
            MyPostRepositoryImpl(
                dispatcher = UnconfinedTestDispatcher(),
                remotePostDataSource = RemotePostDataSourceImpl(remotePostDataMock.httpClient),
                localPostDataSource = LocalPostDataSourceImpl(fakePostDao)
            )
        val data = repository.fetchAllPost()
        assert(data is AppResult.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


