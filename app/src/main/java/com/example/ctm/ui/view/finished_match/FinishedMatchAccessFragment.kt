package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.databinding.FinishedMatchAccessFragmentBinding
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.ui.viewmodel.FinalViewModel

class FinishedMatchAccessFragment: Fragment(), ResultItemAdapter.ActionListener {
    private lateinit var resultItemAdapter: ResultItemAdapter //
    private lateinit var binding: FinishedMatchAccessFragmentBinding
    private lateinit var viewModel: FinalViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[FinalViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.finished_match_access_fragment,
            container, false)

        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.finalViewModel = viewModel
        viewModel.refresh()

        resultItemAdapter = ResultItemAdapter(mutableListOf(), this)

        binding.resultListView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =  resultItemAdapter
        }
        observeViewModel()
        filter()
    }

    private fun observeViewModel(){
        viewModel.finishMatchesLiveData.observe(viewLifecycleOwner){ matches ->
            matches?.let{
                binding.resultListView.visibility= View.VISIBLE
                resultItemAdapter.updateOtherMatches(matches)
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.resultListView.visibility= View.GONE
                }
            }
        }
    }


    private fun filter() {
        val button = binding.FilterInFinishedButton

        button.setOnClickListener {
            findNavController().navigate(R.id.action_finishedMatchAccessFragment_to_menuMatchEndFragment)
        }
    }

    override fun goToMatchDetails(match: Match) {
        val bundle = bundleOf("id" to match.id)

        if(viewModel.viewResult(match.id)) {
            findNavController().navigate(
                R.id.action_finishedMatchAccessFragment_to_expandMatchResultFragment,
                bundle
            )
        } else {
            findNavController().navigate(
                R.id.action_finishedMatchAccessFragment_to_expandMatchWithoutFragment3,
                bundle
            )
        }
    }
}