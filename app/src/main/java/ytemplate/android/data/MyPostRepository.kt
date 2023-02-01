package ytemplate.android.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import ytemplate.android.data.datasource.LocalPostDataSource
import ytemplate.android.data.datasource.RemotePostDataSource
import ytemplate.android.data.model.PostRequest
import ytemplate.android.database.model.Post
import ytemplate.android.infra.AppResult
import javax.inject.Inject

/**
 * MyPostRepository, will provide the post data operation based on the demand.
 */
interface MyPostRepository {
    /**
     * post flow object for getting all post data from locally.
     */
    val post: Flow<List<Post>>

    /**
     * adding post data into the remote and locally.
     */
    suspend fun add(post: PostRequest)

    /**
     * Fetch all post data from remote server and push to the local database.
     */
    suspend fun fetchAllPost(): AppResult<List<Post>?>
}

/**
 * Implementation of PostRepository,
 * @param dispatcher: Coroutine dispatcher
 * @param localPostDataSource: LocalPostDataSource instance for performing local operation.
 * @param remotePostDataSource: RemotePostDataSource instance for performing remote operation
 */
class MyPostRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val localPostDataSource: LocalPostDataSource,
    private val remotePostDataSource: RemotePostDataSource
) :
    MyPostRepository {
    override val post: Flow<List<Post>>
        get() = localPostDataSource.post

    override suspend fun add(post: PostRequest) {
        remotePostDataSource.addPost(post)
    }

    override suspend fun fetchAllPost(): AppResult<List<Post>?> {
        val remoteData = remotePostDataSource.fetchAllPost()
        if (remoteData is AppResult.Success) {
            val dataList = remoteData.data!!.map {
                Post(
                    id = it.id,
                    userId = it.userId,
                    title = it.title,
                    body = it.body
                )
            }
            localPostDataSource.deleteAll()
            localPostDataSource.addAll(
                dataList
            )
            return AppResult.Success(dataList)
        } else {
            return AppResult.Error((remoteData as AppResult.Error).exception)
        }
    }
}