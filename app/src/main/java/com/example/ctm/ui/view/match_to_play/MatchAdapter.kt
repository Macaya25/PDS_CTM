package cl.uandes.pichangapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import cl.uandes.pichangapp.data.datasources.Memory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MatchAdapter (
    private val matches: MutableList<Match>,
    private val actionListener: ActionListener
): RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage = itemView.findViewById<ImageView>(R.id.profile_image)!!
        val textView2 = itemView.findViewById<TextView>(R.id.textView2)!!
        val textView4 = itemView.findViewById<TextView>(R.id.textView4)!!
        val textView6= itemView.findViewById<TextView>(R.id.textView6)!!
        val button = itemView.findViewById<ViewGroup>(R.id.linearLayoutPi)!!
    }

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val resultView: View = inflater.inflate(R.layout.xx_extra, parent, false)
        return ViewHolder(resultView)
    }

    override fun onBindViewHolder(holder: MatchAdapter.ViewHolder, position: Int) {
        val allUsers =repository.getAllUsers().value



        val match: Match = matches[position]
        val userOrganaizer = allUsers?.find{it.email == match?.home_team?.email}
        val userRival = allUsers?.find{it.email == match?.visitor_team?.email}
        val textView2 = holder.textView2
        val textView4 = holder.textView4
        val textView6 = holder.textView6
        val profileImage = holder.profileImage
        val button = holder.button

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        var time = LocalDateTime.parse(match.game_date,dateFormatter)
        var date_hour = " " + time.hour.toString() + ":" + time.minute.toString()
        var date_date = " " + time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()

        textView2.text = date_date
        textView6.text = match.location.place_name
        textView4.text = date_hour
        //if (match.home_team.email != Memory.currentUser?.email){
            //profileImage?.setImageResource(userOrganaizer?.image!!) }
       // else{
           // profileImage?.setImageResource(userRival?.image!!) }


        button.setOnClickListener {
            actionListener.goToMatchDetails(match)
        }
    }

    fun updateOtherMatches(updatedMatches: List<Match>){
        matches.clear()
        matches.addAll((updatedMatches))

    }

    interface ActionListener {
        fun goToMatchDetails(match: Match)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}