package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cl.uandes.pichangapp.databinding.ExpandMatchResultFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.FinalViewModel

class ExpandMatchResultFragment : Fragment() {
    private lateinit var binding: ExpandMatchResultFragmentBinding
    private lateinit var viewModel: FinalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FinalViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.expand_match_result_fragment,
            container, false)

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.finalViewModel = viewModel


        arguments?.let {
            val matchIndex = ExpandMatchResultFragmentArgs.fromBundle(it).id
            viewModel.adapterData(matchIndex)
            //setAttributesToItem(allUserMatches[matchIndex])
        }
    }

    /*
    private fun setAttributesToItem(match: Match) {

        val userOrganaizerTeam = allUsers.find{it.mail == match?.OrganaizerTeam}
        val userRival = allUsers.find{it.mail == match?.RivalTeam}

        val result = _binding?.resultTextView
        val NameTeam1 = _binding?.NameTeam1
        val NameTeam2 = _binding?.NameTeam2
        val DayOfMatch = _binding?.DayOfMatch
        val HourOfMatch = _binding?.HourOfMatch
        val LocationOfMatch = _binding?.LocationOfMatch
        val DescriptionOfMatch = _binding?.DescriptionOfMatch
        val ImageTeam1 = _binding?.ImageTeam1
        val ImageTeam2 = _binding?.ImageTeam2

        result?.text = match?.resultTextView
        NameTeam1?.text = userOrganaizerTeam?.name
        NameTeam2?.text = userRival?.name
        DayOfMatch?.text = match?.DayOfMatch
        HourOfMatch?.text = match?.HourOfMatch
        LocationOfMatch?.text = match?.LocationOfMatch
        DescriptionOfMatch?.text = match?.DescriptionOfMatch
        ImageTeam2?.setImageResource(userRival?.image!!)
        ImageTeam1?.setImageResource(userOrganaizerTeam?.image!!)

    }

     */
}