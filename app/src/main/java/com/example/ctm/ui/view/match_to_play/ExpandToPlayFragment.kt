package cl.uandes.pichangapp

import android.Manifest
import android.Manifest.*
import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.ExpandToPlayFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.MatchToPlayViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class ExpandToPlayFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    private lateinit var map: GoogleMap
    private lateinit var binding: ExpandToPlayFragmentBinding
    private lateinit var viewModel: MatchToPlayViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MatchToPlayViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.expand_to_play_fragment,
            container, false)

        binding.lifecycleOwner = this

        createFragment()
        return binding.root
    }



    private fun createFragment() {
        val mapFragment: SupportMapFragment = parentFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.matchToViewModel = viewModel

        arguments?.let {
            val matchIndex = ExpandMatchResultFragmentArgs.fromBundle(it).id

            viewModel.adapterData(matchIndex)
            //rival_info(allUserMatches[matchIndex])
            //setAttributesToItem(allUserMatches[matchIndex])
            rival_info(matchIndex)
        }
    }

    private fun rival_info(match: Int){
        val button = binding.RivalInfoButton
        val bundle = bundleOf("id" to match)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_expandToPlayFragment_to_rivalInformationFragment2,bundle)
        }
    }




    private fun createMarker() {
        TODO("Not yet implemented")
    }




    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }




    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)

    }




    override fun onMyLocationButtonClick(): Boolean {

        return false
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

/*
    private fun rival_info(match: Match) {
        val button = _binding?.RivalInfoButton
        val bundle = bundleOf("matchIndex" to allUserMatches.indexOf(match))
        button?.setOnClickListener {
            findNavController().navigate(R.id.action_expandToPlayFragment_to_rivalInformationFragment2,bundle)
        }
    }
    */
/*
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun setAttributesToItem(match: Match) {

        val userOrganaizerTeam = allUsers.find{it.mail == match?.OrganaizerTeam}

        val userRival = allUsers.find{it.mail == match?.RivalTeam}

        val textDayOfMatch = _binding?.DataDatePlayMatch
        val NameTeam1 = _binding?.NameTeam1
        val HourOfMatch = _binding?.HourOfMatch
        val DayOfMatch = _binding?.DataDatePlayMatch
        val LocationOfMatch = _binding?.LocationOfMatch
        val DescriptionOfMatch = _binding?.DescriptionOfMatch
        val ImageTeam1= _binding?.ImageTeam1

        textDayOfMatch?.text = match?.DayOfMatch

        HourOfMatch?.text = match?.HourOfMatch
        DayOfMatch?.text = match?.DayOfMatch
        LocationOfMatch?.text = match?.LocationOfMatch
        DescriptionOfMatch?.text = match?.DescriptionOfMatch

        if (match.OrganaizerTeam != currentUser){
            ImageTeam1?.setImageResource(userOrganaizerTeam?.image!!)
            NameTeam1?.text = userOrganaizerTeam?.name}
        else{
            ImageTeam1?.setImageResource(userRival?.image!!)
            NameTeam1?.text = userRival?.name}



    }
*/

}