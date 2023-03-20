package ytemplate.android.data.di

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test
import ytemplate.android.core.data.PostRepositoryImpl
import ytemplate.android.core.data.local.LocalPostDataSourceImpl
import ytemplate.android.core.data.remote.RemotePostDataSourceImpl
import ytemplate.android.core.test.FakePostApi
import ytemplate.android.core.test.FakePostDao
import kotlin.test.assertTrue

class RepositoryModuleTests {
    private val fakePostApi = FakePostApi()
    private val fakePostDao = FakePostDao()

    @Test
    fun `test repository `() {
        val repo = PostRepositoryImpl(UnconfinedTestDispatcher(), LocalPostDataSourceImpl(fakePostDao), RemotePostDataSourceImpl(fakePostApi))
        assertTrue(repo != null)
    }

    @Test
    fun `test local repository `() {
        val repo = LocalPostDataSourceImpl(fakePostDao)
        assertTrue(repo != null)
    }

    @Test
    fun `test remote repository `() {
        val repo = RemotePostDataSourceImpl(fakePostApi)
        assertTrue(repo != null)
    }
}
