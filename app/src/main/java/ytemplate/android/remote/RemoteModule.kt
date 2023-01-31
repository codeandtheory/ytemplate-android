package ytemplate.android.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * RemoteModule, for maintaining the all network related DI operations.
 */
@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    /**
     * Providing the HttpClient instance for the remote communication.
     */
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            engine {

                config {
                    followRedirects(true)
                    connectTimeout(60L, TimeUnit.SECONDS)
                    readTimeout(60L, TimeUnit.SECONDS)
                    writeTimeout(60L, TimeUnit.SECONDS)
                }
                //addInterceptor()
            }
        }
    }
}