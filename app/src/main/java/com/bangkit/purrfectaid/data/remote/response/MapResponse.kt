package com.bangkit.purrfectaid.data.remote.response

import com.google.gson.annotations.SerializedName

data class MapResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem>,

	@field:SerializedName("status")
	val status: String
)

data class Viewport(

	@field:SerializedName("southwest")
	val southwest: Southwest,

	@field:SerializedName("northeast")
	val northeast: Northeast
)

data class Location(

	@field:SerializedName("lng")
	val lng: Any,

	@field:SerializedName("lat")
	val lat: Any
)

data class PlusCode(

	@field:SerializedName("compound_code")
	val compoundCode: String,

	@field:SerializedName("global_code")
	val globalCode: String
)

data class ResultsItem(

	@field:SerializedName("types")
	val types: List<String>,

	@field:SerializedName("business_status")
	val businessStatus: String,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("photos")
	val photos: List<PhotosItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours,

	@field:SerializedName("geometry")
	val geometry: Geometry,

	@field:SerializedName("vicinity")
	val vicinity: String,

	@field:SerializedName("plus_code")
	val plusCode: PlusCode,

	@field:SerializedName("place_id")
	val placeId: String
)

data class Southwest(

	@field:SerializedName("lng")
	val lng: Any,

	@field:SerializedName("lat")
	val lat: Any
)

data class PhotosItem(

	@field:SerializedName("photo_reference")
	val photoReference: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<String>,

	@field:SerializedName("height")
	val height: Int
)

data class Geometry(

	@field:SerializedName("viewport")
	val viewport: Viewport,

	@field:SerializedName("location")
	val location: Location
)

data class Northeast(

	@field:SerializedName("lng")
	val lng: Any,

	@field:SerializedName("lat")
	val lat: Any
)

data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean
)
