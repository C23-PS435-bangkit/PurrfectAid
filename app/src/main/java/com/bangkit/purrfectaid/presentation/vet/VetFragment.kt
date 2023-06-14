package com.bangkit.purrfectaid.presentation.vet

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentVetBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialog

class VetFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentVetBinding? =null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placeClient: PlacesClient
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVetBinding.inflate(inflater, container, false)
        val mapFragment= childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        Places.initialize(requireContext(), "AIzaSyDxsyy92AvMeK538tjyJzTmCh5-QlHlPzs")
        placeClient = Places.createClient(requireContext())

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        showBottomDialog()
        return binding.root
    }
    private fun showBottomDialog() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        dialog.show();
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun fetchNearbyVetLocations(latLng: LatLng) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setLocationBias(RectangularBounds.newInstance(
                LatLngBounds.builder().include(latLng).build()
            ))
            .setQuery("veterinarian")
            .build()

        placeClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val predictions = response.autocompletePredictions
            }
            .addOnFailureListener { exception ->
                Log.e("VetFragment", "Failed to fetch nearby veterinarian locations: ${exception.message}")
            }
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
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val latLng = LatLng(location.latitude, location.longitude)
                        googleMap.isMyLocationEnabled = true
                        fetchNearbyVetLocations(latLng)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("VetFragment", "Failed to get the user's location: ${exception.message}")
                }
        } else {
            requestPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled =true
        googleMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        // Example: Hide the bottom sheet when the map is clicked
//        googleMap.setOnMapClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
    }

}