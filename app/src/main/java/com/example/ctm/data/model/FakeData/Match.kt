package cl.uandes.pichangapp.data.model.FakeData

data class Match(
  open val id_match: String?,
  open val OrganaizerTeam: String?,
  open var RivalTeam: String?,
  open var finish: Boolean,
  open var resultEquip1: Int = 0,
  open var resultEquip2: Int = 0,
  open val resultTextView: String = resultEquip1.toString() + "-" + resultEquip2.toString(),
  open var LocationOfMatch: String?,
  open var DescriptionOfMatch: String?,
  open var HourOfMatch: String?,
  open var DayOfMatch: String?,
)
