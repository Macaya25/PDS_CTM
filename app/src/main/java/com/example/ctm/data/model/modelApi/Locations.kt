package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Locations(
    @SerializedName("locations")
    val locations: List<Location>,
)
