package app.joey.trakttv.di

import app.joey.trakttv.data.MovieService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ServiceModule {
    @Provides
    fun provideMovieService(): MovieService {
        val httpClient = OkHttpClient.Builder()

        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("trakt-api-key", "952eb54bbfb8e4f0ab6363452a9652a20984e8a6b3a7049a0e91d12141ac4569")
                .addHeader("trakt-api-version", "2")
                .build()

            chain.proceed(request)
        }

        httpClient.addInterceptor(interceptor)

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.trakt.tv")
            .client(httpClient.build())
            .build()
            .create(MovieService::class.java)
    }
}
