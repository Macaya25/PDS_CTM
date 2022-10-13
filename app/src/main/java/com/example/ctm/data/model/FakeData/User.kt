package cl.uandes.pichangapp.data.model.FakeData

data class User(
    internal val name: String?,
    internal val mail: String?,
    internal val password: String?,
    internal val category: String?,
    internal val image: Int? = null,
    internal val TotalMatches: Int = 0,
    internal val TotalMatchesWin: Int = 0,
    internal val TotalMatchesLose: Int = 0,
    internal val TotalMatchesTie: Int = 0

){

}


