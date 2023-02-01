package ytemplate.android.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject
import ytemplate.android.data.model.PostDTO
import ytemplate.android.data.model.PostRequest
import ytemplate.android.infra.AppResult
import ytemplate.android.infra.Routes

/**
 * RemotePostDataSource will take care all post related remote operation.
 */
interface RemotePostDataSource {
    /**
     * Fetch all post, will fetch all data from remote server
     */
    suspend fun fetchAllPost(): AppResult<List<PostDTO>?>

    /**
     * Add post will add post data to remote server.
     */
    suspend fun addPost(postRequest: PostRequest): AppResult<Boolean>
}

class RemotePostDataSourceImpl @Inject constructor(private val httpClient: HttpClient) :
    RemotePostDataSource {
    override suspend fun fetchAllPost(): AppResult<List<PostDTO>?> {
        val data = try {
            val data: List<PostDTO> = httpClient.get {
                url(Routes.GET_POST)
            }.body()
            AppResult.Success(data)
        } catch (exc: Exception) {
            AppResult.Error(exc)
        }
        return data
    }

    override suspend fun addPost(postRequest: PostRequest): AppResult<Boolean> {
        return AppResult.Loading
    }
}