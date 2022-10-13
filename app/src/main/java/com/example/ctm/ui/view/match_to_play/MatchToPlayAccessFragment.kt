package cl.uandes.pichangapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.data.model.modelApi.Match
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import cl.uandes.pichangapp.ui.viewmodel.MatchToPlayViewModel
import androidx.lifecycle.ViewModelProvider
import cl.uandes.pichangapp.databinding.MatchToPlayAccessFragmentBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment


class MatchToPlayAccessFragment:  Fragment(), MatchAdapter.ActionListener{

    private lateinit var binding: MatchToPlayAccessFragmentBinding
    private lateinit var matchadapter: MatchAdapter
    private lateinit var viewModel: MatchToPlayViewModel
    private lateinit var map:GoogleMap



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MatchToPlayViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.match_to_play_access_fragment,
            container, false)

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.matchToPlayFragment = viewModel
        viewModel.refresh()
        matchadapter = MatchAdapter(mutableListOf(), this)
        binding.resultLisThisWeek.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =  matchadapter
        }

        observeViewModel()
        filter()

    }

    private fun observeViewModel(){
        viewModel.otherMatchesLiveData.observe(viewLifecycleOwner){ matches ->
            matches?.let{
                binding.resultLisThisWeek.visibility = View.VISIBLE
                matchadapter.updateOtherMatches(matches)
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.resultLisThisWeek.visibility = View.GONE
                }
            }
        }


    }




    private fun filter(){
        val button = binding.FilterInToPlayButton
        button.setOnClickListener {
            findNavController().navigate(R.id.action_matchToPlayAccessFragment_to_filterMenuParticipatingFragment)
        }
    }

    override fun goToMatchDetails(match: Match) {
        val bundle = bundleOf("id" to match.id)
        findNavController().navigate(R.id.action_matchToPlayAccessFragment_to_expandToPlayFragment, bundle)
    }
}


