package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Matches(
    @SerializedName("pichangas")
    val pichangas: List<Match>,
)
