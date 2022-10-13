package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.ExpandMatchAddInformationFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.SearchViewModel


class MatchAddInformationFragment : Fragment() {

    private lateinit var binding: ExpandMatchAddInformationFragmentBinding
    private lateinit var viewModel:SearchViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.expand_match_add_information_fragment,
            container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchViewModel = viewModel

        arguments?.let {
            val matchIndex = ExpandMatchResultFragmentArgs.fromBundle(it).id
            viewModel.adapterData(matchIndex)
           // setAttributesToItem(otherPeoplesMatches[matchIndex])
            //rival_info(matchIndex)
            rival_info(matchIndex) // cambiar a i del usuario rival
        }
        observeViewModel()


    }

    private fun observeViewModel(){
        viewModel.homeLiveData.observe(viewLifecycleOwner){ matches ->
            matches?.let{
                back()
            }
        }

    }

    private fun back(){
        Toast.makeText(context, "Pichanga Inscrita!", Toast.LENGTH_LONG).show()


    }

    private fun rival_info(match: Int){
        val button = binding.RivalInfoButton
        val bundle = bundleOf("id" to match)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_expandMatchAddInformationFragment2_to_rivalInformationFragment,bundle)
        }
    }

    /*
    private fun setAttributesToItem(match: Match) {



        val user=allUsers.find{ it.mail == match?.OrganaizerTeam }

        val MatchDay0 = _binding?.MatchDay
        val NameTeam1 = _binding?.NameTeam1
        val HourOfMatch = _binding?.HourOfMatch
        val LocationOfMatch = _binding?.LocationOfMatch
        val DescriptionOfMatch = _binding?.DescriptionOfMatch
        val ImageTeam1 = _binding?.ImageTeam1


        MatchDay0?.text = match.DayOfMatch
        NameTeam1?.text = user?.name
        HourOfMatch?.text = match?.HourOfMatch
        LocationOfMatch?.text = match?.LocationOfMatch
        DescriptionOfMatch?.text = match?.DescriptionOfMatch
        ImageTeam1?.setImageResource(user?.image!!)
    }

 */
}


