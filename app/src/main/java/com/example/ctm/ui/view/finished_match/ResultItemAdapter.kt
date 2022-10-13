package cl.uandes.pichangapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.pichangapp.data.model.modelApi.Match

class ResultItemAdapter (
    private val matches: MutableList<Match>,
    private val actionListener: ActionListener
): RecyclerView.Adapter<ResultItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultTextView = itemView.findViewById<TextView>(R.id.resultTextView)!!
        val profileImage = itemView.findViewById<ImageView>(R.id.profileImage)!!
        val imageRivalTeam = itemView.findViewById<ImageView>(R.id.imageRivalTeam)!!
        val button = itemView.findViewById<ViewGroup>(R.id.linearLayout2)!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val resultView: View = inflater.inflate(R.layout.result_item_adapter, parent, false)
        return ViewHolder(resultView)
    }

    override fun onBindViewHolder(holder: ResultItemAdapter.ViewHolder, position: Int) {


        val match: Match = matches[position]
        //val allUsers =repository.getAllUsers().value

        //val userOrganaizer = allUsers?.find{it.email == match.home_team.email}
        //val userRival = allUsers?.find{it.email == match.visitor_team?.email}

        val result = holder.resultTextView
        //val profileImage = holder.profileImage
        //val rivalImage = holder.imageRivalTeam
        val button = holder.button

        result.text = match.results
        //profileImage.setImageResource(userOrganaizer?.image!!)
        //rivalImage.setImageResource(userRival?.image!!)
        button.setOnClickListener {
            actionListener.goToMatchDetails(match)
        }
    }

    fun updateOtherMatches(updatedMatches: List<Match>) {
        matches.clear()
        matches.addAll(updatedMatches)
    }

    interface ActionListener {
        fun goToMatchDetails(match: Match)
    }

    override fun getItemCount(): Int {
        return matches.size
    }
}
