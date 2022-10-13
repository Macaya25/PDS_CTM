package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.databinding.SearchCreateAccessFragmentBinding
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.ui.view.home.OrganizedMatchAdapter
import androidx.databinding.DataBindingUtil
import cl.uandes.pichangapp.ui.viewmodel.SearchViewModel
import androidx.lifecycle.ViewModelProvider

class SearchCreateAccessFragment: Fragment(), OrganizedMatchAdapter.ActionListener {

    private lateinit var binding: SearchCreateAccessFragmentBinding
    private lateinit var searchdmatchadapter: OrganizedMatchAdapter
    private lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_create_access_fragment,
            container, false)

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchViewModel = viewModel
        viewModel.refresh()

        searchdmatchadapter = OrganizedMatchAdapter(mutableListOf(), this)

        binding.SearchtListView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =  searchdmatchadapter
        }


        observeViewModel()
        addmatch()
        filter()

    }

    private fun observeViewModel(){
        viewModel.otherMatchesLiveData.observe(viewLifecycleOwner){ matches ->
            matches?.let{
                binding.SearchtListView.visibility= View.VISIBLE
                searchdmatchadapter.updateOtherMatches(matches)
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.SearchtListView.visibility= View.GONE
                }
            }
        }
    }



    private fun addmatch(){
        val button = binding.AddInMatchButton
        button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createMatchFragment)
        }
    }

    private fun filter(){
        val button = binding.FilterInMatchButton

        button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_menuFilterFragment)
        }

    }


    override fun goToMatchDetails(match: Match) {
        val bundle = bundleOf("id" to match.id)
        findNavController().navigate(R.id.action_homeFragment_to_expandMatchAddInformationFragment2, bundle)
    }
}