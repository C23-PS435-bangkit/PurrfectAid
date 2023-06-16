package com.bangkit.purrfectaid.presentation.vet

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.BottomSheetLayoutBinding
import com.bangkit.purrfectaid.databinding.FragmentVetBinding
import com.bangkit.purrfectaid.domain.model.MapRequest
import com.bangkit.purrfectaid.utils.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody

@AndroidEntryPoint
class VetFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentVetBinding? =null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBinding: BottomSheetLayoutBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val viewModel: VetViewModel by viewModels()

    private lateinit var adapter: VetAdapter
    private lateinit var recycleView: RecyclerView
    private var nameList = ArrayList<String>()
    private var avatarList = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVetBinding.inflate(inflater, container, false)
        val mapFragment= childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        showBottomDialog()

        return binding.root
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled =true
        googleMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
    }

    private val requestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
                isGranted: Boolean ->
            if(isGranted){
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled=true
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val latLng = LatLng(location.latitude, location.longitude)
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng, 70f
                            )
                        )
                        fetchNearbyVetLocations(latLng)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Failed to get the user's location: ${exception.message}")
                }
        } else {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun fetchNearbyVetLocations(latLng:LatLng) {
        val request = MapRequest(
            "${latLng.latitude},${latLng.longitude}",
            50000,
            "veterinary",
            getString(R.string.maps_api_key)
        )

        viewModel.getLocation(request).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    val results = it.data.results
                    for (i in results) {
                        val location = LatLng(i.geometry.location.lat as Double,
                            i.geometry.location.lng as Double
                        )

                        nameList.add(i.name)
                        for(photo in i.photos){
                            viewModel.getImage(photo.photoReference, getString(R.string.maps_api_key)).observe(viewLifecycleOwner){
                                res ->
                                Log.d("TAGUT", res.toString())
                                getPhoto(res)
                            }
                            avatarList.add(photo.htmlAttributions[0])
                        }

                        val markerOptions =
                            MarkerOptions()
                                    .position(location)
                                    .title(i.name)
                                    .snippet(i.vicinity)
                        googleMap.addMarker(markerOptions)

                        recycleView = bottomSheetBinding.rvNearestVet
                        adapter = VetAdapter(nameList,avatarList)
                        recycleView.adapter= adapter
                    }
                }
                is Result.Loading -> {
                }
                is Result.Error -> {
                    Log.e("MAP ERROR", "err: ${it.errorMessage}")
                }
            }
        }
    }

    private fun getPhoto(res: Result<ResponseBody>) {
        when(res){
            is Result.Success ->{
                Log.d(TAG, res.data.byteStream().toString())
            }
            is Result.Error -> {
                Log.e("FETCHING PHOTO ERROR", "err: ${res.errorMessage}")
            }
            Result.Loading -> {
            }
        }
    }

    private fun showBottomDialog() {

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetBinding = BottomSheetLayoutBinding.bind(bottomSheetView)

        bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bottomSheetDialog.setContentView(bottomSheetView)
        val bottomSheetLayout = bottomSheetView.parent as View
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)

        bottomSheetBehavior.peekHeight = 500
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()
    }


    companion object{
        private const val TAG =".VetFragment"
    }
}
