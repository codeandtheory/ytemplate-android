package ytemplate.android.core.network.apis

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ytemplate.android.core.network.Routes
import ytemplate.android.core.network.model.PostDTO
import ytemplate.android.core.network.model.PostRequest
import javax.inject.Inject

/**
 * Post api impl
 *
 * @property httpClient
 * @constructor Create empty Post api impl
 */
class PostApiImpl @Inject constructor(private val httpClient: HttpClient) : PostApi {
    override suspend fun get(): List<PostDTO>? {
        val data: List<PostDTO> = httpClient.get {
            url(Routes.POST)
        }.body()
        return data
    }

    override suspend fun post(postRequest: PostRequest): Boolean {
// not implemented
        return false
    }
}
