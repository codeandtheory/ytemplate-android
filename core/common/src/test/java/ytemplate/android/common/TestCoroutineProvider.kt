package ytemplate.android.common

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import ytemplate.android.core.common.di.DispatcherModule
import ytemplate.android.core.common.di.DispatcherModule_ProvideNetworkDispatcherFactory.provideNetworkDispatcher

/**
 * Test coroutine provider
 *
 * @constructor Create empty Test coroutine provider
 */
class TestCoroutineProvider {

    /**
     * Result_catches_errors
     *
     */
    @Test
    fun result_catches_errors() = runTest {
        val dispatcher = provideNetworkDispatcher(DispatcherModule())
        assertTrue(dispatcher != null)
    }
}
