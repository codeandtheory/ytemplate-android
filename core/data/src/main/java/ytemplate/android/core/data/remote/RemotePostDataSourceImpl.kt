package ytemplate.android.core.data.remote

import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.data.util.toPostList
import ytemplate.android.core.network.apis.PostApi
import javax.inject.Inject

/**
 * Remote post data source impl
 *
 * @property api
 * @constructor Create empty Remote post data source impl
 */
class RemotePostDataSourceImpl @Inject constructor(private val api: PostApi) :
    RemotePostDataSource {
    override suspend fun fetchAllPost(): AppResult<List<Post>?> {
        val data = try {
            val data: List<Post>? = api.get()?.toPostList()
            AppResult.Success(data)
        } catch (exc: Exception) {
            AppResult.Error(exc)
        }
        return data
    }

    override suspend fun addPost(postRequest: Post): AppResult<Boolean> {
        // todo implemenet post api
        return AppResult.Loading
    }
}
