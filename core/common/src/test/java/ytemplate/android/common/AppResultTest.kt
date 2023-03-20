/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ytemplate.android.common

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ytemplate.android.core.common.AppResult
import ytemplate.android.core.common.asResult

/**
 * App result test
 *
 * @constructor Create empty App result test
 */
@kotlinx.coroutines.ExperimentalCoroutinesApi
class AppResultTest {
    /**
     * Result success
     *
     */
    @Test
    fun test_result_as_success() = runTest {
        val appResult = 1
        flow {
            emit(appResult)
        }
            .asResult()
            .test {
                assertEquals(AppResult.Loading, awaitItem())
                assertEquals(AppResult.Success(appResult), awaitItem())
                awaitComplete()
            }
    }

    /**
     * Result_catches_errors
     *
     */
    @Test
    fun result_catches_errors() = runTest {
        flow {
            emit(1)
            val appResult = AppResult.Error(Exception("Test Done"))
            throw Exception("Test Done")
        }
            .asResult()
            .test {
                assertEquals(AppResult.Loading, awaitItem())
                assertEquals(AppResult.Success(1), awaitItem())

                when (val errorResult = awaitItem()) {
                    is AppResult.Error -> assertEquals(
                        "Test Done",
                        errorResult.exception?.message
                    )
                    AppResult.Loading,
                    is AppResult.Success
                    -> throw IllegalStateException(
                        "The flow should have emitted an Error Result"
                    )
                }

                awaitComplete()
            }
    }
}
