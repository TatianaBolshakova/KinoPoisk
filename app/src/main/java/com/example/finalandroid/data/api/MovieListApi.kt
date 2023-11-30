package com.example.finalandroid.data.api

import android.provider.MediaStore
import com.example.finalandroid.data.models.Images
import com.example.finalandroid.data.models.InfoActorItem
import com.example.finalandroid.data.models.InfoActorsItem
import com.example.finalandroid.data.models.InfoMovie
import com.example.finalandroid.data.models.Item
import com.example.finalandroid.data.models.ListIdAndCountries
import com.example.finalandroid.data.models.MovieList
import com.example.finalandroid.data.models.SimilarFilms
import com.example.finalandroid.data.models.Video
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

interface MovieListApi {
    // Премьеры
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun premieres(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int,
    ): MovieList

    // Триллеры
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films?genres=1")
    suspend fun thrillers(@Query("page") page: Int): MovieList

    // id стран и жанров
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun idAndCountries(): ListIdAndCountries


    // Топ фильмов (рандомный выбор из категории)
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top")
    suspend fun topList(
        @Query("type") type: String,
        @Query("page") page: Int
        ): MovieList

    // Сериалы (рандомный выбор из категории)
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun tvSeries(
        @Query("type") type: String,
       // @Query("countries") countries: Int,
        @Query("page") page: Int
    ): MovieList

    // Информация о фильме
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun film(
        @Path("id") id: Int
    ): InfoMovie

    // Список актеров
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun allActors(
        @Query("filmId") filmId: Int
    ): List<InfoActorsItem>

    // Галерея фильма
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun images(
        @Path("id") id: Int
    ): Images

    // Похожие фильмы
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun similar(
        @Path("id") id: Int
    ): SimilarFilms

    // Информация об актере
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun actor(
        @Path("id") id: Int
    ): InfoActorItem

    // ????
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun filmActor(
        @Query("ratingFrom") ratingFrom: Int = 1,
        @Query("imdbId") imdbId: String
    ): MovieList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/videos")
    suspend fun video(
        @Path("id") id: Int
    ): Video
//AIzaSyAG-U7opubpmYNq5zcWNjQcDKYmCLowfnA
   // @Headers("X-API-KEY: AIzaSyB8tEEjNA6hbv67rxvRVWHT_LTWKOVsCL8")
    @Headers("Content-Type: application/json")
    @GET("{url}")
    suspend fun videoPlay(
        @Path("url") url: String
    )




    // Поиск
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun getSearch(
        @Query("keyword") keyword: String = "keyword",
        @Query("countries") countries: Array<Int> = arrayOf(1),
        @Query("genres") genres: Array<Int> = arrayOf(1),
        @Query("order") order: String = "RATING",
        @Query("type") type: String = "ALL",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
    ): MovieList



    private companion object {
        private const val api_key = "51379e98-b7c3-4b66-be90-8da65604f1b7"
       // private const val api_key = "7d439c2f-5cb6-4448-96e1-1fc7ba004253" // key 2
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


     val retrofitX = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
        .create(MovieListApi::class.java)
   //val getApi = retrofitX.create(MovieListApi::class.java)
