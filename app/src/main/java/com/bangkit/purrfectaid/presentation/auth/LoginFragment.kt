package com.bangkit.purrfectaid.presentation.auth

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.purrfectaid.databinding.FragmentLoginBinding
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.utils.Constants.BASE_URL
import com.bangkit.purrfectaid.utils.Result
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.IOException
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
//    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

//        binding.loadingBar.

//        googleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        oneTapClient = Identity.getSignInClient(requireContext())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("585155626503-7hqu1np44o9hho4qmq5cg5843uc6238t.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        setupView()

        return binding.root
    }

//    private fun siggn() {
//        val intent = googleSignInClient.signInIntent
//        startActivityForResult(intent, 400)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 400) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            task.result.
//        }
//    }

    private fun setupGoogle() {

    }

    private fun signInGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { res ->
                try {
                    startIntentSenderForResult(
                        res.pendingIntent.intentSender, 400,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("GAKBISA", "ERRor: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.e("SAMA AJA", e.localizedMessage ?: "GAK BISA JUGA")
            }
    }

//    val intentSender = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode == Activity.RESULT_OK) {
//            try {
//
//            }
//        }
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            400 -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.id
                    loginWithGoogle(idToken)

                    Log.d("DATA", credential.displayName.toString())
                } catch (e: ApiException) {
                    Log.e("GAGAl", e.toString())
                }
            }
        }
    }

    private fun loginWithGoogle(idToken: String) {
        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType {
                return "application/json; charset=utf-8".toMediaType()
            }

            override fun writeTo(sink: BufferedSink) {
                val json = Gson().toJson(mapOf("email" to idToken))
                sink.write(json.toByteArray())
            }
        }


        val request = Request.Builder()
            .url(BASE_URL + "users/protected")
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("LOGINWITHGOOGLE", "Failed: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body.string()
                Log.d("RESPO", responseBody)
                val jsonObject = JSONObject(responseBody)
                val token = jsonObject.getString("token")
                Log.d("TOKEN", token)
            }
        })

    }

//    private fun Any.toRequestBody(mediaType: MediaType = "application/json; charset=utf-8".toMediaType()): RequestBody {
//        val json = Gson().toJson(this)
//        return json.toRequestBody(mediaType)
//    }


    private fun setupView() {
        binding.btnToRegister.setOnClickListener {
            val toRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(toRegister)
        }

        binding.btnLogin.setOnClickListener {
            val loginRequest = LoginRequest(
                email = binding.edLoginEmail.text.toString(),
                password = binding.edLoginPassword.text.toString()
            )
            login(loginRequest)
//            val toHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//            findNavController().navigate(toHome)
        }

        binding.btnLoginWithGoogle.setOnClickListener {
            signInGoogle()
//            viewModel.registerOrLoginWithGoogle().observe(viewLifecycleOwner) {
//                when (it) {
//                    is Result.Success -> {
//                        if (it.data.status == 200) {
//                            Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
//                            val tohome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//                            findNavController().navigate(tohome)
//                        }
//                    }
//
//                    is Result.Loading -> {
//
//                    }
//
//                    is Result.Error -> {
//                        Log.e("Error Login Google", "Error: ${it.errorMessage}")
//                    }
//                }
//            }
        }
    }

    private fun login(loginRequest: LoginRequest) {
        viewModel.login(loginRequest).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.status == 200) {
                        hideLoading()
                        Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                        val toHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(toHome)
                    }
                }

                is Result.Loading -> {
                    showLoading()
                }

                is Result.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Error Login", "Error: ${it.errorMessage}")
                }
            }
        }
    }

    private fun showLoading() {
        binding.loadingBar.visibility = View.VISIBLE
        binding.loadingBar.setOnClickListener {  }
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}