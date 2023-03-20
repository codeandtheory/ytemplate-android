package ytemplate.android.core.test

import ytemplate.android.core.database.dao.PostDao
import ytemplate.android.core.database.model.PostEntity

/**
 * Dummy DAO class for unit testing the db operation.
 */
class FakePostDao : PostDao {
    private val data = mutableMapOf<Int, PostEntity>()
    override fun getAllPost(): List<PostEntity> {
        return data.values.toList()
    }

    override suspend fun insert(post: PostEntity) {
        data[post.id] = post
    }

    override suspend fun insert(post: List<PostEntity>) {
        data.putAll(post.associateBy({ it.id }, { it }))
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    override suspend fun deleteItem(postId: Int) {
        data.remove(postId)
    }
}
