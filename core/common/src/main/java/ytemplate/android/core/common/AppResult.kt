package ytemplate.android.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * App result
 *
 * @param T
 * @constructor Create empty App result
 */
sealed interface AppResult<out T> {
    /**
     * Success
     *
     * @param T
     * @property data
     * @constructor Create empty Success
     */
    data class Success<T>(val data: T) : AppResult<T>

    /**
     * Error
     *
     * @property exception
     * @constructor Create empty Error
     */
    data class Error(val exception: Throwable? = null) : AppResult<Nothing>

    /**
     * For maintaining the loading state,
     */
    object Loading : AppResult<Nothing>
}

/**
 * As result
 *
 * @param T
 * @return
 */
fun <T> Flow<T>.asResult(): Flow<AppResult<T>> {
    return this
        .map<T, AppResult<T>> {
            AppResult.Success(it)
        }
        .onStart { emit(AppResult.Loading) }
        .catch { emit(AppResult.Error(it)) }
}
