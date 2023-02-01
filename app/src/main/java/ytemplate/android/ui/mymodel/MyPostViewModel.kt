package ytemplate.android.ui.mymodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ytemplate.android.data.MyPostRepository
import ytemplate.android.database.model.Post
import javax.inject.Inject

sealed class MyModelUiState {
    object Loading : MyModelUiState()
    data class Success(val data: List<Post>) : MyModelUiState()
    data class Error(val throwable: Throwable) : MyModelUiState()
}

/**
 * MyPostViewModel, for maintaining the MyModelScreen state.
 * @param myPostRepository: Repository instance.
 */
@HiltViewModel
class MyPostViewModel @Inject constructor(private val myPostRepository: MyPostRepository) :
    ViewModel() {
    val uiState: StateFlow<MyModelUiState> =
        myPostRepository.post.map { MyModelUiState.Success(it) }
            .catch { MyModelUiState.Error(it) }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                MyModelUiState.Loading
            )

    fun getAllPost(){
        viewModelScope.launch(Dispatchers.IO) {
            myPostRepository.fetchAllPost()
        }
    }

}