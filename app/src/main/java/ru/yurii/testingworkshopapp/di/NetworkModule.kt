package ru.yurii.testingworkshopapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.yurii.testingworkshopapp.data.api.TodoistApiService
import java.util.concurrent.TimeUnit

/**
 * @author y.anisimov
 */
class NetworkModule {

    private val baseUrl = "https://api.todoist.com/rest/"
    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)

    private val retrofit = retrofitBuilder.build()

    val api: TodoistApiService by lazy { retrofit.create(TodoistApiService::class.java) }
}

class ApiKeyInterceptor: Interceptor {

    companion object {
        private const val API_KEY = "ab1b875a2120cbcc3bd365ae3baaced1fbec05c5"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val requestBuilder = origin.newBuilder()
            .header("Authorization", "Bearer $API_KEY")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
