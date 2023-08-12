package com.learner.funzo.retrofit

import okhttp3.OkHttpClient

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class BackendClientGenerator {
    companion object {
        private val BASE_URL = "http://10.0.2.2:8080/"
        private val httpClient = OkHttpClient.Builder()

        private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build())

        private val retrofit = builder.build()

        @JvmStatic
        fun <S> createClient(serviceClass: Class<S>?): S {
            return retrofit.create(serviceClass)
        }
    }
}
