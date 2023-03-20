package ytemplate.android.core.network.model

/**
 * Post d t o
 *
 * @property userId
 * @property id
 * @property title
 * @property body
 * @constructor Create empty Post d t o
 */
@kotlinx.serialization.Serializable
data class PostDTO(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
