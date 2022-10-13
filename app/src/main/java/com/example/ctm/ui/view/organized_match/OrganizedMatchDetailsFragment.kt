package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.ExpandOrganizedInformationFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.OrganizedMatchesViewModel

class OrganizedMatchDetailsFragment : Fragment() {

    private lateinit var binding: ExpandOrganizedInformationFragmentBinding
    private lateinit var viewModel: OrganizedMatchesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[OrganizedMatchesViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.expand_organized_information_fragment,
            container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.organizedMatchesViewModel = viewModel

        arguments?.let {
            var matchIndex = ExpandMatchResultFragmentArgs.fromBundle(it).id
            viewModel.adapterData(matchIndex)
            //setAttributesToItem(allUserOrganizedMatches[matchIndex])
        }

        delete_match()
        observeViewModel()
    }



    private fun delete_match() {
        val button = binding.DeleteMatchButton

        button.setOnClickListener {
            findNavController().navigate(R.id.action_expandOrganizedInformationFragment_to_organizedMatchAccessFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.deleteLiveData.observe(viewLifecycleOwner) { areValid ->
            areValid?.let {
                if (it) {
                    findNavController().navigate(R.id.action_expandOrganizedInformationFragment_to_organizedMatchAccessFragment)
                } else {
                    Toast.makeText(context, "No se logró eliminar Pichanga", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

/*
    private fun setAttributesToItem(match: Match) {

        val user=allUsers.find{ it.mail == match?.OrganaizerTeam }


        val NameTeam = _binding?.NameTeam
        val DayOfMatch = _binding?.DayOfMatch
        val HourOfMatch = _binding?.HourOfMatch
        val LocationOfMatch = _binding?.LocationOfMatch
        val DescriptionOfMatch = _binding?.DescriptionOfMatch
        val ImageTeam = _binding?.ImageTeam

        NameTeam?.text = user?.name
        DayOfMatch?.text = match?.DayOfMatch
        HourOfMatch?.text = match?.HourOfMatch
        LocationOfMatch?.text = match?.LocationOfMatch
        DescriptionOfMatch?.text = match?.DescriptionOfMatch
        ImageTeam?.setImageResource(user?.image!!)

    }
    *
 */
}