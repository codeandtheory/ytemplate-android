package ytemplate.android.ui.mymodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ytemplate.android.data.MyModelRepository

@OptIn(ExperimentalCoroutinesApi::class)
class MyModelViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test initial ui state`() = runTest {
        val viewModel = MyModelViewModel(FakeMyModelRepository())
        assert(viewModel.uiState.first() == MyModelUiState.Loading)
    }

    @Test
    fun `test ui state after adding item`() = runTest {
        val repository = FakeMyModelRepository()
        val viewModel = MyModelViewModel(repository)
        val collectJob = launch {
            viewModel.uiState.collect()
        }
        assert(viewModel.uiState.first() == MyModelUiState.Loading)
        viewModel.addModel("test")
        repository.emit()
        val data = viewModel.uiState.value
        assert(data is MyModelUiState.Success)
        collectJob.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

private class FakeMyModelRepository : MyModelRepository {
    private val data = mutableListOf<String>()
    override val myModel = MutableSharedFlow<List<String>>()
    override suspend fun add(name: String) {
        data.add(name)
    }

    suspend fun emit() = myModel.emit(data)
}
