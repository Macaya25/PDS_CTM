package cl.uandes.pichangapp.ui.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class OrganizedMatchAdapter (
    private val matches: MutableList<Match>,
    private val actionListener: ActionListener

): RecyclerView.Adapter<OrganizedMatchAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage = itemView.findViewById<ImageView>(R.id.profile_image)!!
        val textView2 = itemView.findViewById<TextView>(R.id.textView2)!!
        val textView4 = itemView.findViewById<TextView>(R.id.textView4)!!
        val textView6= itemView.findViewById<TextView>(R.id.textView6)!!
        val button = itemView.findViewById<ViewGroup>(R.id.linearLayoutPi)!!
    }

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizedMatchAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val resultView: View = inflater.inflate(R.layout.xx_extra, parent, false)
        return ViewHolder(resultView)
    }

    override fun onBindViewHolder(holder: OrganizedMatchAdapter.ViewHolder, position: Int) {


        val allUsers =repository.getAllUsers().value

        val match: Match = matches[position]
        val userOrganaizer = allUsers?.find{it.email == match.home_team.email}
        val textView2 = holder.textView2
        val textView4 = holder.textView4
        val textView6 = holder.textView6
        val profileImage = holder.profileImage
        val button = holder.button

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        var date_hour = " "
        var date_date = " "
        if (match.game_date == null){
            date_hour = "22:10"
            date_date = "12/05/2022"
        }
        else{
            var time = LocalDateTime.parse(match.game_date,dateFormatter)
            date_hour = " " + time.hour.toString() + ":" + time.minute.toString()
            date_date = " " + time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()
        }



        textView2.text = date_date
        textView6.text = match.location.place_name
        textView4.text = date_hour
        //profileImage.setImageResource(userOrganaizer?.image!!)

        button.setOnClickListener {
            actionListener.goToMatchDetails(match)
        }
    }
    fun updateOtherMatches(updatedMatches: List<Match>){
        matches.clear()
        matches.addAll((updatedMatches))

    }


    private fun matchType(match: Match): String {
        return match.javaClass.simpleName
    }

    interface ActionListener {
        fun goToMatchDetails(match: Match)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}