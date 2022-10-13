package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id")
    var id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("authentication_token")
    val authentication_token: String?
)
