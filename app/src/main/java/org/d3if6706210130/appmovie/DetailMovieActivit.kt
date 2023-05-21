package org.d3if6706210130.appmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import org.d3if6706210130.appmovie.databinding.ActivityDetailMovieBinding
import org.d3if6706210130.appmovie.modeldata.MovieDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val b = intent.extras
        val i = b?.getString("imdbid")
        val apikey = "5a7dc32a"

        RClient.instances.getDataMovie(i, apikey).enqueue(object : Callback<MovieDetailData> {
            override fun onResponse(
                call: Call<MovieDetailData>,
                response: Response<MovieDetailData>
            ) {
                binding.tvTahun.text = response.body()?.Year
                binding.tvJudulmovie.text = response.body()?.Title
                binding.tvTglrilis.text = response.body()?.rilis


                Glide.with(this@DetailMovieActivity).load(response.body()?.gambar)
                    .into(binding.imgGambarposter)
            }

            override fun onFailure(call: Call<MovieDetailData>, t: Throwable) {
                // Handle failure
            }
        })

        val shareButton = binding.shareButton
        shareButton.setOnClickListener {
            shareData()
        }
    }

    private fun shareData() {
        val textToShare = "dibagikan"

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
        sendIntent.type = "text/plain"

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
