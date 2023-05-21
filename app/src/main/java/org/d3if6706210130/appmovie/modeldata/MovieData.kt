package org.d3if6706210130.appmovie.modeldata

import com.google.gson.annotations.SerializedName

class MovieData (
    val Year:String, val Title:String,
    @SerializedName("Poster") val gambar:String,
    @SerializedName("imdbID") val idmovie:String,


    )

