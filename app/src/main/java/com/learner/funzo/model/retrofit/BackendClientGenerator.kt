package com.learner.funzo.model.retrofit

import okhttp3.OkHttpClient

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.RuntimeException

object BackendClientGenerator {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    @JvmStatic
    fun <S> createClient(serviceClass: Class<S>?): S {
        return try{
            retrofit.create(serviceClass)
        } catch (e: Exception) {
            throw RuntimeException("Unable to create retro client. className: ${serviceClass!!.name}")
        }
    }
}
