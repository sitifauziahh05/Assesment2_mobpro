package org.d3if6706210130.appmovie

import org.d3if6706210130.appmovie.modeldata.MovieDetailData
import org.d3if6706210130.appmovie.modeldata.SearchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/")
    fun getMovies(
        @Query("s")s:String?,
        @Query("apikey") apikey:String
    ):Call<SearchData>

    @GET("/")
    fun getDataMovie(
        @Query("i")i:String?,
        @Query("apikey") apikey:String,

    ):Call<MovieDetailData>
}