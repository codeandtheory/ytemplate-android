package ytemplate.android.ui.mymodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ytemplate.android.data.MyModelRepository
import javax.inject.Inject

sealed class MyModelUiState {
    object Loading : MyModelUiState()
    data class Success(val data: List<String>) : MyModelUiState()
    data class Error(val throwable: Throwable) : MyModelUiState()
}

@HiltViewModel
class MyModelViewModel @Inject constructor(private val myModelRepository: MyModelRepository) :
    ViewModel() {
    val uiState: StateFlow<MyModelUiState> =
        myModelRepository.myModel.map { MyModelUiState.Success(it) }
            .catch { MyModelUiState.Error(it) }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                MyModelUiState.Loading
            )

    fun addModel(name: String) {
        viewModelScope.launch {
            myModelRepository.add(name)
        }
    }
}