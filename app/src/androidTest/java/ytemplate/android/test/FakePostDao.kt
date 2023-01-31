package ytemplate.android.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ytemplate.android.database.model.Post
import ytemplate.android.database.model.PostDao

/**
 * Dummy DAO class for unit testing the db operation.
 */
class FakePostDao : PostDao {
    private val data = mutableListOf<Post>()
    private val postFlow = MutableSharedFlow<List<Post>>()
    override fun getAllPost(): Flow<List<Post>> {
        return postFlow
    }

    override suspend fun insert(post: Post) {
        data.add(post)
    }

    override suspend fun insert(post: List<Post>) {
        print("Data:${post.size}")
        data.addAll(post)
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    suspend fun emitData() {
        postFlow.emit(data)
    }
}