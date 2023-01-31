package ytemplate.android.data.datasource

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ytemplate.android.data.model.PostDTO
import ytemplate.android.data.model.PostRequest
import ytemplate.android.infra.AppRequest
import javax.inject.Inject

/**
 * RemotePostDataSource will take care all post related remote operation.
 */
interface RemotePostDataSource {
    /**
     * Fetch all post, will fetch all data from remote server
     */
    suspend fun fetchAllPost(): AppRequest<List<PostDTO>?>

    /**
     * Add post will add post data to remote server.
     */
    suspend fun addPost(postRequest: PostRequest): AppRequest<Boolean>
}

class RemotePostDataSourceImpl @Inject constructor(private val httpClient: HttpClient) :
    RemotePostDataSource {
    override suspend fun fetchAllPost(): AppRequest<List<PostDTO>?> {
        val data = try {
            val data: List<PostDTO> = httpClient.get {
                url("https://jsonplaceholder.typicode.com/posts")
            }.body()
            AppRequest.Success(data)
        } catch (exc: Exception) {
            AppRequest.Error(exc)
        }
        return data
    }

    override suspend fun addPost(postRequest: PostRequest): AppRequest<Boolean> {
        return AppRequest.Loading
    }
}