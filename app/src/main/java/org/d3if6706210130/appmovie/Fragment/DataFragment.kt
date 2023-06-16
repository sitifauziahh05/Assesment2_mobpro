package org.d3if6706210130.appmovie.Fragment

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.d3if6706210130.appmovie.MainActivity
import org.d3if6706210130.appmovie.MovieAdapter
import org.d3if6706210130.appmovie.R
import org.d3if6706210130.appmovie.RClient
import org.d3if6706210130.appmovie.databinding.FragmentDataBinding
import org.d3if6706210130.appmovie.modeldata.MovieData
import org.d3if6706210130.appmovie.modeldata.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<MovieData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager = LinearLayoutManager(context)

        val bundle = arguments
        val s = bundle?.getString("carimovie")
        val apikey = "5a7dc32a"

        RClient.instances.getMovies(s, apikey).enqueue(object : Callback<SearchData> {
            override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                val cekRes = response.body()?.res

                if (cekRes == "True") {
                    response.body()?.let { list.addAll(it.data) }
                    val adapter = MovieAdapter(list, requireContext())
                    binding.rvData.adapter = adapter

                } else {
                    Toast.makeText(context, "Movie not found", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SearchData>, t: Throwable) {
                // Handle failure case
            }
        })

        // Menjadwalkan pekerjaan latar belakang dengan WorkManager
        scheduleBackgroundWork(requireActivity().application)

        // Meminta izin notifikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun scheduleBackgroundWork(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

}
