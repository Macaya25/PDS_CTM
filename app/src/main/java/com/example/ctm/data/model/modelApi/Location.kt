package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id")
    var id: Long,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("place_name")
    val place_name: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("updated_a")
    val updated_a: String?
)