package ytemplate.android.core.network.model

/**
 * Post request
 *
 * @property title
 * @property body
 * @property userId
 * @constructor Create empty Post request
 */
@kotlinx.serialization.Serializable
data class PostRequest(
    val title: String,
    val body: String,
    val userId: Int
)
