package com.learner.funzo.model.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * A utility class for generating Retrofit clients.
 */
object ClientGenerator {

    /**
     * The Retrofit instance configured with a base URL, Gson converter factory,
     * and OkHttpClient with custom timeouts.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build())
        .build()

    /**
     * Creates a Retrofit client for the specified service interface.
     *
     * @param serviceClass The class of the service interface.
     * @return An instance of the Retrofit service interface.
     * @throws RuntimeException if there is an error creating the Retrofit client.
     */
    @JvmStatic
    fun <S> createClient(serviceClass: Class<S>): S {
        return try{
            retrofit.create(serviceClass)
        } catch (e: Exception) {
            throw RuntimeException("Unable to create retro client. className: ${serviceClass.name}")
        }
    }
}
