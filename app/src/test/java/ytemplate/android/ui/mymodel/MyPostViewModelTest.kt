package ytemplate.android.ui.mymodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ytemplate.android.data.FakePostDao
import ytemplate.android.data.MyPostRepository
import ytemplate.android.data.MyPostRepositoryImpl
import ytemplate.android.data.PlaceHolderData
import ytemplate.android.data.RemotePostDataMock
import ytemplate.android.data.datasource.LocalPostDataSourceImpl
import ytemplate.android.data.datasource.RemotePostDataSourceImpl
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class MyPostViewModelTest {
    private lateinit var remotePostDataMock: RemotePostDataMock
    private lateinit var fakePostDao: FakePostDao
    private lateinit var repository: MyPostRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        remotePostDataMock = RemotePostDataMock()
        fakePostDao = FakePostDao()
        repository =
            MyPostRepositoryImpl(
                dispatcher = UnconfinedTestDispatcher(),
                remotePostDataSource = RemotePostDataSourceImpl(remotePostDataMock.httpClient),
                localPostDataSource = LocalPostDataSourceImpl(fakePostDao)
            )
    }

    @Test
    fun `test initial data is empty state`() = runTest {
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test {
            val firstDataState = awaitItem()
            assert(firstDataState is MyModelUiState.Loading)
            fakePostDao.emitData()
            val dataState = awaitItem()
            assert((dataState as MyModelUiState.Success).data.isEmpty())
        }
    }

    @Test
    fun `test get data success state`() = runTest {
        remotePostDataMock.setExpectSuccess()
        remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test(timeout = 10000.milliseconds) {
            val firstDataState = awaitItem()
            assert(firstDataState is MyModelUiState.Loading)
            viewModel.getAllPost()
            fakePostDao.emitData()
            val responseData = awaitItem()
            assert(responseData is MyModelUiState.Success)
            cancelAndConsumeRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
