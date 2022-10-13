package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    var id: Long?,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("category")
    val category: String?
    )