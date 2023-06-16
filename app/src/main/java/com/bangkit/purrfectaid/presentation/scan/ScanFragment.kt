package com.bangkit.purrfectaid.presentation.scan

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.purrfectaid.databinding.FragmentScanBinding
import com.bangkit.purrfectaid.presentation.diagnose.DiagnoseBottomSheet
import com.bangkit.purrfectaid.utils.Result
import com.bangkit.purrfectaid.utils.createFile
import com.bangkit.purrfectaid.utils.toImageMultiPart
import com.bangkit.purrfectaid.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()
    private lateinit var imageCapture: ImageCapture
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        requestPermissionCamera()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGallery.setOnClickListener {
            launchGallery()
        }

        binding.btnCancelScan.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnScan.setOnClickListener {
            showLoading()
            takeImage()
        }
    }

    private fun showLoading() {
        binding.loadingBar.visibility = View.VISIBLE
        binding.loadingBar.setOnClickListener {  }
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.GONE
    }

    private fun takeImage() {
        val imageCap = imageCapture

        val imageFile = createFile(requireContext())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

        imageCap.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    predict(imageFile.toImageMultiPart())
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "Failed to capture image", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }

    private fun launchGallery() {
        pickPhotoFromGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val pickPhotoFromGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val file = uriToFile(uri, requireActivity())
                predict(file.toImageMultiPart())
            }
        }

    @SuppressLint("InflateParams")
    private fun predict(image: MultipartBody.Part) {
        viewModel.predict(image).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    hideLoading()
                    Log.d("BERHASIL", it.data.toString())
                    val diagnoseSheet = DiagnoseBottomSheet()
                    val bundle = Bundle()
                    bundle.putString("diagnose", it.data.diagnose)
                    bundle.putString("treatment", it.data.treatment)
                    bundle.putBoolean("isFromDiagnoseHistory", false)
                    diagnoseSheet.arguments = bundle
                    diagnoseSheet.show(childFragmentManager, "Expanded")
                }

                is Result.Loading -> {

                }

                is Result.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "Gagal mengirimkan gambar, cek koneksi anda", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR PREDICT", "err: ${it.errorMessage}")
                }
            }
        }
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission not granted", Toast.LENGTH_SHORT)
                .show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun requestPermissionCamera() {
        requestPermission.launch(Manifest.permission.CAMERA)
    }

    private fun startCamera() {
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())

        processCameraProvider.addListener({
            val cameraProvider: ProcessCameraProvider = processCameraProvider.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.pvScan.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e("SCANFRAGMENT", "Gagal: $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

}