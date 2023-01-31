package ytemplate.android.data.model

@kotlinx.serialization.Serializable
data class PostDTO(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)