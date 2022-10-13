package cl.uandes.pichangapp.data.model.modelApi

import com.google.gson.annotations.SerializedName

data class Match (
    @SerializedName("id")
    var id: Int,
    @SerializedName("home_team")
    val home_team: Team,
    @SerializedName("visitor_team")
    val visitor_team: Team?,
    @SerializedName("location")
    val location: Location,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("game_date")
    val game_date: String,
    @SerializedName("results")
    val results: String?,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String
    )