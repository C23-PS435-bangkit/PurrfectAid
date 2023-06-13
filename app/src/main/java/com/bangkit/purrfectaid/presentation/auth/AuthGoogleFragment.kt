package com.bangkit.purrfectaid.presentation.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentAuthGoogleBinding
import com.bangkit.purrfectaid.utils.Constants


class AuthGoogleFragment : Fragment() {

    private var _binding: FragmentAuthGoogleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthGoogleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            webviewGoogle.loadUrl(Constants.BASE_URL + "users/google")
            webviewGoogle.settings.userAgentString = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"
            webviewGoogle.settings.javaScriptEnabled = true

            webviewGoogle.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    webviewGoogle.evaluateJavascript("(function() { return JSON.stringify(data); })();") {
                        val json = it?.replace("\"", "")
                        Log.d("JSON", "$json")
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}