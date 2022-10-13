package cl.uandes.pichangapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.CreateMatchFragmentBinding
import cl.uandes.pichangapp.ui.view.search_match.DatePickerFragment
import cl.uandes.pichangapp.ui.view.search_match.TimePickerFragment
import cl.uandes.pichangapp.ui.viewmodel.CreateMatchViewModel
import cl.uandes.pichangapp.ui.viewmodel.SearchViewModel
import java.util.*



class CreateMatchFragment: Fragment(), View.OnClickListener {
    private lateinit var binding: CreateMatchFragmentBinding
    private lateinit var viewModel: CreateMatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[CreateMatchViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_match_fragment,
            container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume(){
        super.onResume()
        var locations = viewModel.getLocationsList()
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, locations)
        binding.autoCompleteTextView3.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createMatchViewModel = viewModel
        observeViewModel()
        dateInput()
        hourInput()

    }

    private fun observeViewModel() {
        viewModel.matchIsValid.observe(viewLifecycleOwner) { areValid ->
            areValid?.let {
                findNavController().navigate(R.id.action_createMatchFragment_to_homeFragment)
            }
        }
    }


    private fun dateInput(){
        val button = binding.editFecha
        button.setOnClickListener {
        val date = DatePickerFragment{year,mont,day -> showResult(year,mont,day )}
        date.show(parentFragmentManager,"datePicker")
    }}

    private fun hourInput(){
        val button = binding.editTextHora
        button.setOnClickListener {

            val hour = TimePickerFragment{hour,minute-> showResulthour(hour,minute )}
            hour.show(parentFragmentManager,"timePicker")
        }}


    private fun showResulthour(hour: Int, minute: Int) {
        val shour = hour.toString()
        val sminute = minute.toString()
        binding.editTextHora.text = "$shour:$sminute"

        viewModel.datahour("T$shour:$sminute:00")

    }
    private fun showResult(year: Int, mounth: Int, day: Int) {
        val syear = year.toString()
        val smounth = mounth.toString()
        val sday = day.toString()
        binding.editFecha.text = "$syear-$smounth-$sday"
        viewModel.dataDate("$syear-$smounth-$sday")
//dia mes ano
    }




    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}

private fun Button.setText() {
    TODO("Not yet implemented")
}

