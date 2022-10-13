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

import cl.uandes.pichangapp.databinding.UserProfileFragmentBinding
import cl.uandes.pichangapp.ui.viewmodel.ProfileViewModel

class UserProfileFragment: Fragment() {

    private lateinit var binding:UserProfileFragmentBinding
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_profile_fragment,
            container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCurrentUser()
        binding.profileViewModel = viewModel

        profileAction()
        //setAttributesToItem()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun observeViewModel() {
        viewModel.logout.observe(viewLifecycleOwner) { areValid ->
            areValid?.let {
                if (it) {
                    findNavController().navigate(R.id.action_userProfileFragment_to_login_navigation)
                }
            }
        }
    }


    private fun profileAction() {
        val profileButton = binding.signOutButton

        profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_login_navigation)
        }
    }
/*
    private fun setAttributesToItem() {
        val user=allUsers.find{ it.mail == currentUser }
        val teamImage = binding.teamImage
        val textTeamNameBackground = binding.textTeamNameBackground
        val textTeamMailBackground = binding.textTeamMailBackground
        val textTeamCategoryBackground = binding.textTeamCategoryBackground

        if (user != null) {
            teamImage.setImageResource(user.image!!)
        }
        textTeamNameBackground.text = teamName
        textTeamMailBackground.text = currentUser
        textTeamCategoryBackground.text = category

    }

 */
}

