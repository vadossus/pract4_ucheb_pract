package com.bignerdranch.android.application_practica2.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bignerdranch.android.application_practica2.R

private const val ARG_IMDB_URL = "imdb_url"

class MovieWebFragment : Fragment() {
    private var imdburl: String? = null
    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imdburl = it.getString(ARG_IMDB_URL)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        if (actionBar.is)

        val rootView = inflater.inflate(R.layout.fragment_movie_web, container, false)
        webView = rootView.findViewById(R.id.webView)

        imdburl?.let {
            webView.loadUrl(it)
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }





        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(imdbUrl: String): MovieWebFragment {
            return MovieWebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMDB_URL, imdbUrl)
                }
            }
        }
    }







}