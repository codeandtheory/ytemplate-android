package ytemplate.android.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.data.local.LocalPostDataSource
import ytemplate.android.core.data.remote.RemotePostDataSource
import javax.inject.Inject

/**
 * Post repository impl
 *
 * @property dispatcher
 * @property localPostDataSource
 * @property remotePostDataSource
 * @constructor Create empty Post repository impl
 */
class PostRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val localPostDataSource: LocalPostDataSource,
    private val remotePostDataSource: RemotePostDataSource
) :
    PostRepository {
    private val _feed = MutableSharedFlow<List<Post>>()
    override val feed: SharedFlow<List<Post>> = _feed.asSharedFlow()

    override suspend fun add(postItem: Post) {
        localPostDataSource.add(postItem)
        _feed.emit(localPostDataSource.getPost())
    }

    override suspend fun delete(postItem: Post) {
        localPostDataSource.delete(postItem)
        _feed.emit(localPostDataSource.getPost())
    }

    override suspend fun loadInitialData(): AppResult<List<Post>?> {
        val remoteData = remotePostDataSource.fetchAllPost()
        return if (remoteData is AppResult.Success) {
            val dataList = remoteData.data
            localPostDataSource.deleteAll()
            if (dataList != null) {
                localPostDataSource.addAll(dataList)
                _feed.emit(localPostDataSource.getPost())
            }
            AppResult.Success(dataList)
        } else {
            AppResult.Error((remoteData as AppResult.Error).exception)
        }
    }
}
