package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cl.uandes.pichangapp.databinding.RivalInformationFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.RivalViewModel

class RivalInformationFragment: Fragment() {
    private lateinit var binding: RivalInformationFragmentBinding
    private lateinit var viewModel: RivalViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[RivalViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.rival_information_fragment,
            container, false)

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rivalViewModel = viewModel


        arguments?.let {
            val rivalId = RivalInformationFragmentArgs.fromBundle(it).id
            viewModel.adapterData(rivalId)
            //setAttributesToItem(allUserMatches[matchIndex])
        }
    }


/*
    private fun setAttributesToItem(match: Match) {
        val userRival = allUsers.find{it.mail == match?.RivalTeam}
        val userOrganaizerTeam = allUsers.find{it.mail == match?.OrganaizerTeam}

        val NameTeam = _binding?.NameTeam
        val ImageTeam = _binding?.ImageTeam
        val TotalMatches = _binding?.TotalMatches
        val Victories = _binding?.Victories
        val Ties = _binding?.Ties
        val Lose = _binding?.Lose

        if (match.OrganaizerTeam != currentUser){
            NameTeam?.text = userOrganaizerTeam?.name
            TotalMatches?.text = userOrganaizerTeam?.TotalMatches.toString()
            Victories?.text = userOrganaizerTeam?.TotalMatchesWin.toString()
            Ties?.text = userOrganaizerTeam?.TotalMatchesTie.toString()
            Lose?.text = userOrganaizerTeam?.TotalMatchesTie.toString()
            ImageTeam?.setImageResource(userOrganaizerTeam?.image!!)
            }
        else{
            NameTeam?.text = userRival?.name
            TotalMatches?.text = userRival?.TotalMatches.toString()
            Victories?.text = userRival?.TotalMatchesWin.toString()
            Ties?.text = userRival?.TotalMatchesTie.toString()
            Lose?.text = userRival?.TotalMatchesTie.toString()
            ImageTeam?.setImageResource(userRival?.image!!)}



    }

 */
}