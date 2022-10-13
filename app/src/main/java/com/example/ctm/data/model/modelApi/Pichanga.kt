package cl.uandes.pichangapp.data.model.modelApi



import com.google.gson.annotations.SerializedName

data class Pichanga (
    @SerializedName("id")
    var id: Long?,
    @SerializedName("home_team_id")
    val home_team_id: Long,
    @SerializedName("visitor_team_id")
    val visitor_team_id: Long?,
    @SerializedName("location_id")
    val location_id: Long?,
    @SerializedName("instructions")
    val instructions: String?,
    @SerializedName("game_date")
    val gameDate: String?,
    @SerializedName("results")
    val results: String?,

)