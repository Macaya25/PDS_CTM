package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class IdPichanga(
    @SerializedName("pichanga")
    val pichanga: Match
)
