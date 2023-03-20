package ytemplate.android.feature.post

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
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.data.PostRepository
import javax.inject.Inject

/**
 * My model ui state
 *
 * @constructor Create empty My model ui state
 */
sealed class MyPostModelUiState {
    object Loading : MyPostModelUiState()

    /**
     * Success
     *
     * @property data
     * @constructor Create empty Success
     */
    data class Success(val data: List<Post>) : MyPostModelUiState()

    /**
     * Error
     *
     * @property throwable
     * @constructor Create empty Error
     */
    data class Error(val throwable: Throwable) : MyPostModelUiState()
}

/**
 * MyPostViewModel, for maintaining the MyModelScreen state.
 *
 * @property myPostRepository
 * @constructor Create empty My post view model
 */
@HiltViewModel
class MyPostViewModel @Inject constructor(private val myPostRepository: PostRepository) :
    ViewModel() {
    val uiState: StateFlow<MyPostModelUiState> = myPostRepository.feed.map {
        if (it.isNotEmpty()) {
            MyPostModelUiState.Success(it)
        } else {
            MyPostModelUiState.Error(Exception("empty list"))
        }
    }.catch { MyPostModelUiState.Error(it) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        MyPostModelUiState.Loading
    )

    /**
     * Get all post
     *
     */
    fun getAllPost() {
        viewModelScope.launch(Dispatchers.IO) {
            myPostRepository.loadInitialData()
        }
    }

    /**
     * add new post
     *
     */
    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            myPostRepository.add(post)
        }
    }

    /**
     * delete existing post
     *
     */
    fun deletePostItem(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            myPostRepository.delete(post)
        }
    }
}
