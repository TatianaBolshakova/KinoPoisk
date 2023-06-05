package com.example.finalandroid.data

import com.example.finalandroid.data.models.MovieList
import com.example.finalandroid.data.models.PagedMovie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieListApi {
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun premieres(@Query("year") year: Int, @Query("month") month: String): MovieList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun populars(@Query("page") page: Int): PagedMovie



    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun topList(@Query("page") page: Int): PagedMovie

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films?countries=1")
    suspend fun tvSeries(
        @Query("page") page: Int,
        @Query("genres") genres: Int = 1,
        @Query("type") type: String ="TV_SERIES"
    ): MovieList

    private companion object {
        private const val api_key = "51379e98-b7c3-4b66-be90-8da65604f1b7"
    }
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://kinopoiskapiunofficial.tech")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MovieListApi::class.java)!!