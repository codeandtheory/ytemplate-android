package ytemplate.android.data.datasource

import kotlinx.coroutines.flow.Flow
import ytemplate.android.database.model.Post
import ytemplate.android.database.model.PostDao
import javax.inject.Inject


/**
 * Local post data source will provide all the local(Database) data for the post
 */
interface LocalPostDataSource {
    /**
     * Post entity database flow, for fetching all post data
     */
    val post: Flow<List<Post>>

    /**
     * Add post entity to local database
     */
    suspend fun add(post: Post)

    /**
     * Add list of post object  to local database
     */
    suspend fun addAll(post: List<Post>)

    /**
     * Clear post table in locally
     */
    suspend fun deleteAll()
}

class LocalPostDataSourceImpl @Inject constructor(private val postDao: PostDao) :
    LocalPostDataSource {
    override val post: Flow<List<Post>>
        get() = postDao.getAllPost()

    override suspend fun add(post: Post) {
        postDao.insert(post)
    }

    override suspend fun addAll(post: List<Post>) {
        postDao.insert(post)
    }

    override suspend fun deleteAll() {
        postDao.deleteAll()
    }
}