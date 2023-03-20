package ytemplate.android.core.data

import kotlinx.coroutines.flow.SharedFlow
import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.model.Post

interface PostRepository {
    /**
     * post flow object for getting all post data from locally.
     */
    val feed: SharedFlow<List<Post>>

    /**
     * adding post data into the remote and locally.
     */
    suspend fun add(postItem: Post)

    /**
     * deleting post data from the local repo.
     */
    suspend fun delete(postItem: Post)

    /**
     * Fetch all post data from remote server and push to the local database.
     */
    suspend fun loadInitialData(): AppResult<List<Post>?>
}
