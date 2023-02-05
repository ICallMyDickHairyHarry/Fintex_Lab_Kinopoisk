package com.example.fintexlabkinopoisk.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://kinopoiskapiunofficial.tech/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KinopoiskApiService {
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/top")
    suspend fun getTopFilms(@Query("type") type: String): TopFilms

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/{id}")
    suspend fun getSpecificFilm(@Path("id") id: Int): SpecificFilm
}

object KinopoiskApi {
    val retrofitService : KinopoiskApiService by lazy {
        retrofit.create(KinopoiskApiService::class.java) }
}