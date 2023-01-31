package ytemplate.android.data.model

@kotlinx.serialization.Serializable
data class PostRequest(
    val title: String,
    val body: String,
    val userId: Int
)