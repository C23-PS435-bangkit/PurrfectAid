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
import com.bangkit.purrfectaid.domain.model.LoginGoogleRequest
import com.bangkit.purrfectaid.domain.model.LoginRequest
import com.bangkit.purrfectaid.utils.Constants.BASE_URL
import com.bangkit.purrfectaid.utils.Result
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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
import kotlin.random.Random

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

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

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 400)
//        Log.d("LOGIN", "GOOGLE")
//        oneTapClient.beginSignIn(signInRequest)
//            .addOnSuccessListener(requireActivity()) { res ->
//                try {
//                    startIntentSenderForResult(
//                        res.pendingIntent.intentSender, 400,
//                        null, 0, 0, 0, null
//                    )
//                } catch (e: IntentSender.SendIntentException) {
//                    Log.e("GAKBISA", "ERRor: ${e.localizedMessage}")
//                }
//            }
//            .addOnFailureListener(requireActivity()) { e ->
//                Log.e("SAMA AJA", e.localizedMessage ?: "GAK BISA JUGA")
//            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            400 -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    handleSignInResult(task)
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
////                    val idToken = credential.
//
//
//                    Log.d("DATA", " ${credential.displayName} + ${credential.familyName} + ${credential.givenName}")
                } catch (e: ApiException) {
                    Log.e("GAGAl", e.toString())
                }
            }
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val acc = task.getResult(ApiException::class.java)

            Log.d(
                "DATA", """
                ${acc.id},
                ${acc.account}
                ${acc.email}
                ${acc.givenName}
                ${acc.photoUrl}
            """.trimIndent()
            )

            sendGoogleDataToApiForLogin(acc!!)
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Gagal login dengan google", Toast.LENGTH_SHORT).show()
            Log.e("Login Google", "Error: $e")
        }
    }

    private fun sendGoogleDataToApiForLogin(acc: GoogleSignInAccount) {

        val loginGoogleRequest = LoginGoogleRequest(
            google_id = Random.nextInt(2000, 100000),
            google_email = acc.email!!,
            google_name = acc.givenName!!,
            google_picture = acc.photoUrl.toString()
        )

        viewModel.loginGoogle(loginGoogleRequest).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT)
                        .show()
                    val toHome = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(toHome)
                }

                is Result.Loading -> {
                    showLoading()
                }

                is Result.Error -> {
                    hideLoading()
                    Log.e("LOGIN GOOGLE", it.errorMessage)
                    Toast.makeText(
                        requireContext(),
                        "Gagal login dengan google, silahkan coba lagi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


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
        }
    }

    private fun login(loginRequest: LoginRequest) {
        viewModel.login(loginRequest).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.status == 200) {
                        hideLoading()
                        Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT)
                            .show()
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
        binding.loadingBar.setOnClickListener { }
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}