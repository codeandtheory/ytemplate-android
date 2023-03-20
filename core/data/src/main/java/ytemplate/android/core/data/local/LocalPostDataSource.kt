package ytemplate.android.core.data.local

import ytemplate.android.core.common.model.Post

/**
 * Local post data source will provide all the local(Database) data for the post
 */
interface LocalPostDataSource {
    /**
     * getPost(), for fetching all post data as Flow
     */
    suspend fun getPost(): List<Post>

    /**
     * Add post entity to local database
     */
    suspend fun add(post: Post)

    /**
     * delete post entity from local database
     */
    suspend fun delete(post: Post)

    /**
     * Add list of post object  to local database
     */
    suspend fun addAll(post: List<Post>)

    /**
     * Clear post table in locally
     */
    suspend fun deleteAll()
}
