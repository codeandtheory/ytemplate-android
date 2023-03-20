package ytemplate.android.core.data.remote

import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.model.Post

/**
 * RemotePostDataSource will take care all post related remote operation.
 */
interface RemotePostDataSource {
    /**
     * Fetch all post, will fetch all data from remote server
     */
    suspend fun fetchAllPost(): AppResult<List<Post>?>

    /**
     * Add post will add post data to remote server.
     */
    suspend fun addPost(postRequest: Post): AppResult<Boolean>
}
