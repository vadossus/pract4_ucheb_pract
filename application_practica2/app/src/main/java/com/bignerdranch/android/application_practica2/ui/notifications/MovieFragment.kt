package com.bignerdranch.android.application_practica2.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.application_practica2.MovieAPI
import com.bignerdranch.android.application_practica2.R
import com.bignerdranch.android.application_practica2.adapters.MovieAdapter
import com.bignerdranch.android.application_practica2.databinding.FragmentMovieBinding
import com.bignerdranch.android.application_practica2.ui.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var context: Context
    private lateinit var adapter: MovieAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)

        context = this.requireContext()


        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val moviesAPI: MovieAPI = retrofit.create(MovieAPI::class.java)



        moviesAPI.getData().enqueue(object: Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    Log.d("r",response.body().toString())
                    response.body()?.takeIf { it.isNotEmpty() }?.get(0)?.let {
                        val movies = response.body() ?: emptyList()
                        adapter = MovieAdapter(movies, context)
                        binding.rvMovies.adapter = adapter
                    }
                }
                else
                {
                    Log.e("Error","Connection is failed (1)")
                }
            }
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.e("Error","Connection is failed (2)")
            }
        }
        )

        val dashboardViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        return binding.root


    }

    private fun openWebView(imdbUrl: String) {
        val movieWebFragment = MovieWebFragment.newInstance(imdbUrl)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, movieWebFragment) // Use your own fragment container ID
            ?.addToBackStack(null)
            ?.commit()
    }







}