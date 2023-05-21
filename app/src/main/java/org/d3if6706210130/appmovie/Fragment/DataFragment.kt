package org.d3if6706210130.appmovie.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if6706210130.appmovie.MovieAdapter
import org.d3if6706210130.appmovie.R
import org.d3if6706210130.appmovie.RClient
import org.d3if6706210130.appmovie.databinding.FragmentDataBinding
import org.d3if6706210130.appmovie.modeldata.MovieData
import org.d3if6706210130.appmovie.modeldata.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<MovieData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager = LinearLayoutManager(context)

        val bundle = arguments
        val s = bundle?.getString("carimovie")
        val apikey = "5a7dc32a"

        RClient.instances.getMovies(s,apikey).enqueue(object  : Callback<SearchData>{
            override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                 //val responseCode = response.code()
                val  cekRes = response.body()?.res

                if(cekRes == "True"){
                    response.body()?.let { list.addAll(it.data) }
                    val adapter = MovieAdapter(list, requireContext())
                    binding.rvData.adapter = adapter

                    }else{
                        Toast.makeText(context,"Movie not found", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<SearchData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}