package ytemplate.android.infra

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * AppRequest is generic interface for maintaining every request status.
 */
sealed interface AppResult<out T> {
    /**
     * For the success request
     * @param data: Original data
     */
    data class Success<T>(val data: T) : AppResult<T>

    /**
     * For the failure request
     * @param exception: Exception
     */
    data class Error(val exception: Throwable? = null) : AppResult<Nothing>

    /**
     * For maintaining the loading state,
     */
    object Loading : AppResult<Nothing>
}

/**
 * Extension for converting to flow to result
 */
fun <T> Flow<T>.asResult(): Flow<AppResult<T>> {
    return this
        .map<T, AppResult<T>> {
            AppResult.Success(it)
        }
        .onStart { emit(AppResult.Loading) }
        .catch { emit(AppResult.Error(it)) }
}