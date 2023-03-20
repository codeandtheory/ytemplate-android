package ytemplate.android.core.test

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * RemotePostDataMock will provide dummy http client for get post api.
 *
 */
class RemotePostDataMock {

    private var isSuccess: Boolean = false

    private var expectedOutput: String? = null

    fun setExpectSuccess() {
        isSuccess = true
    }
    fun setExpectFailure() {
        isSuccess = false
    }

    fun setExpectedResponse(data: String?) {
        expectedOutput = data
    }

    private val mockEngine = MockEngine { req ->
        handleGetPostRequest(req) ?: errorResponse()
    }
    val httpClient = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }
    private fun MockRequestHandleScope.handleGetPostRequest(httpRequest: HttpRequestData): HttpResponseData? {
        if (httpRequest.url.encodedPath.contains("/post").not()) {
            return null
        }
        val statusCode = if (isSuccess && expectedOutput != null) {
            HttpStatusCode.OK
        } else {
            HttpStatusCode.InternalServerError
        }
        return respond(
            content = expectedOutput ?: "",
            status = statusCode,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    private fun MockRequestHandleScope.errorResponse(): HttpResponseData {
        return respond(
            content = "",
            status = HttpStatusCode.BadRequest,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
}
