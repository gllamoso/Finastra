package dev.gtcl.nasarover.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
class MarsRoverResponse(val photos: List<Photo>): Parcelable

@Parcelize
data class Photo(
    val id: Int,
    val sol: Int,
    val camera: Camera,
    @Json(name = "img_src")
    val imgSrc: String,
    val rover: Rover,
    @Json(name = "earth_date")
    val earthDate: String
    ) : Parcelable

@Parcelize
data class Camera(
    val name: String,
    @Json(name = "full_name")
    val fullName: String
    ): Parcelable

@Parcelize
data class Rover(
    val name: String,
    @Json(name = "landing_date")
    val landingDate: String,
    @Json(name = "launch_date")
    val launchDate: String,
    val status: String
    ): Parcelable