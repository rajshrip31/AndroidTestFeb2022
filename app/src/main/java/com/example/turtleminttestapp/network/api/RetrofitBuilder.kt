package com.example.turtleminttestapp.network.api

import com.example.turtleminttestapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

object RetrofitBuilder {

    private const val BASE_URL = "https://api.github.com/repos/"

    private val logger: Logger = Logger.getLogger("Turtlemint RetrofitApiClient")

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.addInterceptor { chain ->
            var originalRequest = chain.request()
            var request = originalRequest.newBuilder()
                .addHeader("Cache-Control", "no-cache")
                .build()

            chain.proceed(request)
        }

        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            var loggingInterceptor =
                HttpLoggingInterceptor { message ->
                    logger.info("RetrofitLog:"+
                        message
                    )
                }

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        return okHttpClient.build()
    }


    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}