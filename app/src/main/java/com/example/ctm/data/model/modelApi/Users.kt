package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("users")
    val users: List<Users_structure>,
)
