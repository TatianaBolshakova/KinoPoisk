package com.example.finalandroid.data.api

import com.example.finalandroid.data.models.Images
import com.example.finalandroid.data.models.InfoActorItem
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.models.InfoMovie
import com.example.finalandroid.data.models.ListIdAndCountries
import com.example.finalandroid.data.models.MovieList
import com.example.finalandroid.data.models.SimilarFilms
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

interface MovieListApi {
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun premieres(@Query("year") year: Int, @Query("month") month: String): MovieList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films?genres=1")
    suspend fun thrillers(): MovieList
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun idAndCountries(): ListIdAndCountries

//    @Headers("X-API-KEY: $api_key")
//    @GET("/api/v2.2/films?ratingFrom=7")
//    suspend fun topList(
//
//    ): MovieList
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top")
    suspend fun topList(
    @Query("type") type: String
    ): MovieList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun tvSeries(
        @Query("type") type: String ,
        @Query("countries") countries: Int
    ): MovieList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun film(
        @Path ("id") id: Int
    ): InfoMovie
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun allActors(
        @Query("filmId") filmId: Int
    ): List<InfoActorsItem>
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun images(
        @Path ("id") id: Int
    ): Images
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun similar(
        @Path ("id") id: Int
    ): SimilarFilms
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun actor(
        @Path ("id") id: Int
    ): InfoActorItem
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun actorFilm(
        @Path ("id") id: Int
    ): InfoActorItem



    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun filmActor(
        @Query("ratingFrom") ratingFrom: Int = 1,
        @Query("imdbId") imdbId: String
    ): MovieList

    private companion object {
       // private const val api_key = "51379e98-b7c3-4b66-be90-8da65604f1b7"
        private const val api_key = "7d439c2f-5cb6-4448-96e1-1fc7ba004253" //Denis


    }
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MovieListApi::class.java)

//object RetrofitInstance {
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create())
//        .build()
//    val getApi = retrofit.create(MovieListApi::class.java)
//}