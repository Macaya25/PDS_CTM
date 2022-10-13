package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.databinding.OrganizedMatchAccessFragmentBinding
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.ui.view.home.OrganizedMatchAdapter
import cl.uandes.pichangapp.ui.viewmodel.OrganizedMatchesViewModel

class OrganizedMatchAccessFragment : Fragment(), OrganizedMatchAdapter.ActionListener{
    private lateinit var binding: OrganizedMatchAccessFragmentBinding

    private lateinit var viewModel: OrganizedMatchesViewModel

    private lateinit var organizedmatchadapter: OrganizedMatchAdapter
    var MatchDate= ""
    var Hour= ""
    var Place= ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[OrganizedMatchesViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.organized_match_access_fragment,
            container, false)

        binding.lifecycleOwner = this
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.organizedMatchesFragment = viewModel
        viewModel.refresh()
        organizedmatchadapter = OrganizedMatchAdapter(mutableListOf(), this)
        binding.OrganizedtListView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =  organizedmatchadapter
        }


        //expand()
        filter()
        //setAttributesToItem()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.otherMatchesLiveData.observe(viewLifecycleOwner){ matches ->
            matches?.let{
                binding.OrganizedtListView.visibility= View.VISIBLE
                organizedmatchadapter.updateOtherMatches(matches)
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.OrganizedtListView.visibility = View.GONE
                }
            }
        }
    }

    private fun expand(){
        val button = binding?.linearLayout4

        button?.setOnClickListener {

            val bundle = bundleOf("MatchDay" to MatchDate, "dataHour" to Hour, "dataPlace" to Place)
            Navigation.findNavController(it).navigate(R.id.action_organizedMatchAccessFragment_to_expandOrganizedInformationFragment, bundle)
        }
    }

    private fun filter(){
        val button = binding?.FilterInOrganizedButton

        button?.setOnClickListener {
            findNavController().navigate(R.id.action_organizedMatchAccessFragment_to_organizedFilterFragment2)
        }
    }

    private fun setAttributesToItem() {
        /*
        val match = allMatches.find{ it.OrganaizerTeam == currentUser }
        val MatchDay = _binding?.MatchDay
        val dataHour = _binding?.dataHour
        val data_place = _binding?.dataPlace

        MatchDay?.text = match?.DayOfMatch
        dataHour?.text=match?.HourOfMatch
        data_place?.text=match?.LocationOfMatch

        MatchDate= match?.DayOfMatch!!
        Hour= match?.HourOfMatch!!
        Place= match?.LocationOfMatch!!

         */

    }

    override fun goToMatchDetails(match: Match) {
        val bundle = bundleOf("id" to match.id)
        findNavController().navigate(R.id.action_organizedMatchAccessFragment_to_expandOrganizedInformationFragment, bundle)
    }
}
