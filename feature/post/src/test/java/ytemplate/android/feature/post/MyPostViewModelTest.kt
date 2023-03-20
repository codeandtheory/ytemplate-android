package ytemplate.android.feature.post

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
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.test.FakePostRepository
import ytemplate.android.core.test.PlaceHolderData
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

/**
 * My post view model test
 *
 * @constructor Create empty My post view model test
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MyPostViewModelTest {
    private lateinit var repository: FakePostRepository

    /**
     * Set up
     *
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FakePostRepository()
    }

    /**
     * Test initial data is empty state
     *
     */
    @Test
    fun `test initial data is empty state`() = runTest {
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test {
            val firstDataState = awaitItem()
            assert(firstDataState is MyPostModelUiState.Loading)
            repository.emitData()
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test get data success state
     *
     */
    @Test
    fun `test get data success state`() = runTest {
        repository.fakePostApi.remotePostDataMock.setExpectSuccess()
        repository.fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test(timeout = 10000.milliseconds) {
            val firstDataState = awaitItem()
            assert(firstDataState is MyPostModelUiState.Loading)
            viewModel.getAllPost()
            repository.loadInitialData()
            val responseData = awaitItem()
            assert(responseData is MyPostModelUiState.Success)
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test delete item from local
     *
     */
    @Test
    fun `test delete single data state`() = runTest {
        repository.fakePostApi.remotePostDataMock.setExpectSuccess()
        repository.fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test(timeout = 20000.milliseconds) {
            val firstDataState = awaitItem()
            assert(firstDataState is MyPostModelUiState.Loading)
            viewModel.getAllPost()
            repository.loadInitialData()
            val responseData = awaitItem()
            assert(responseData is MyPostModelUiState.Success)
            val responseItems = (responseData as MyPostModelUiState.Success).data
            viewModel.deletePostItem(responseItems[0])
            val responseAfterDelete = awaitItem()
            assert(responseAfterDelete is MyPostModelUiState.Success)
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test add item
     *
     */
    @Test
    fun `test add single data state`() = runTest {
        repository.fakePostApi.remotePostDataMock.setExpectSuccess()
        repository.fakePostApi.remotePostDataMock.setExpectedResponse(PlaceHolderData.DUMMY_DATA_GET_ALL_POST)
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test(timeout = 20000.milliseconds) {
            val firstDataState = awaitItem()
            assert(firstDataState is MyPostModelUiState.Loading)
            val dummyPost = Post(1, 1, "dummy title", "Body")
            viewModel.addPost(dummyPost)
            val responseData = awaitItem()
            assert(responseData is MyPostModelUiState.Success)
            val responseItems = (responseData as MyPostModelUiState.Success).data
            assertEquals(responseItems[0].id, dummyPost.id)
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test ErrorState
     *
     */
    @Test
    fun `test errorState`() = runTest {
        repository.fakePostApi.remotePostDataMock.setExpectFailure()
        repository.fakePostApi.remotePostDataMock.setExpectedResponse(null)
        val viewModel = MyPostViewModel(repository)
        viewModel.uiState.test(timeout = 20000.milliseconds) {
            val firstDataState = awaitItem()
            assert(firstDataState is MyPostModelUiState.Loading)
            viewModel.getAllPost()
            val responseData = awaitItem()
            assert(responseData is MyPostModelUiState.Error)
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Tear down
     *
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
