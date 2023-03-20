package ytemplate.android.core.network.di

import io.ktor.serialization.kotlinx.json.*
import ytemplate.android.core.network.CONNECTION_READ_TIME_OUT
import ytemplate.android.core.network.CONNECTION_TIMEOUT
import ytemplate.android.core.network.CONNECTION_WRITE_TIME_OUT
import java.util.concurrent.TimeUnit

/**
 * Network module
 *
 * @constructor Create empty Network module
 */
@dagger.Module
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
class NetworkModule {
    /**
     * Provide http client
     *
     * @return
     */
    @dagger.Provides
    @javax.inject.Singleton
    fun provideHttpClient(): io.ktor.client.HttpClient {
        return io.ktor.client.HttpClient(io.ktor.client.engine.okhttp.OkHttp) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(
                    kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            engine {
                config {
                    followRedirects(true)
                    connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    readTimeout(CONNECTION_READ_TIME_OUT, TimeUnit.SECONDS)
                    writeTimeout(
                        CONNECTION_WRITE_TIME_OUT,
                        TimeUnit.SECONDS
                    )
                }
                // addInterceptor()
            }
        }
    }
}
