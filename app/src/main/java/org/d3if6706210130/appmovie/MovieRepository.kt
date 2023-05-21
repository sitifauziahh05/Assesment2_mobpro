package org.d3if6706210130.appmovie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>
}
class MovieRepository {
    private val apiService: MovieApiService = Retrofit.Builder()
        .baseUrl("https://example.com/api/") // Ganti dengan URL yang sesuai
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApiService::class.java)

    suspend fun getMovies(): List<Movie> {
        return apiService.getMovies()
    }
}