package ytemplate.android.infra

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * AppRequest is generic interface for maintaining every request status.
 */
sealed interface AppRequest<out T> {
    /**
     * For the success request
     * @param data: Original data
     */
    data class Success<T>(val data: T) : AppRequest<T>

    /**
     * For the failure request
     * @param exception: Exception
     */
    data class Error(val exception: Throwable? = null) : AppRequest<Nothing>

    /**
     * For maintaining the loading state,
     */
    object Loading : AppRequest<Nothing>
}

/**
 * Extension for converting to flow to result
 */
fun <T> Flow<T>.asResult(): Flow<AppRequest<T>> {
    return this
        .map<T, AppRequest<T>> {
            AppRequest.Success(it)
        }
        .onStart { emit(AppRequest.Loading) }
        .catch { emit(AppRequest.Error(it)) }
}