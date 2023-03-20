package ytemplate.android.core.common.model

/**
 * Post
 *
 * @property id
 * @property userId
 * @property title
 * @property body
 * @constructor Create empty Post
 */
data class Post(
    val id: Int = 0,
    val userId: Int?,
    val title: String,
    val body: String
)
