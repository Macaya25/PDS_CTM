package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.FragmentLoginAccessBinding
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cl.uandes.pichangapp.ui.viewmodel.LoginViewModel
import androidx.databinding.DataBindingUtil

class LoginAccessFragment : Fragment() {

    private lateinit var binding: FragmentLoginAccessBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_access,
            container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginViewModel = viewModel

        register()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.credentialsAreValid.observe(viewLifecycleOwner) { areValid ->
            areValid?.let {
                if (it) {
                    findNavController().navigate(R.id.action_loginAccessFragment_to_search_match_navigation)
                } else {
                    Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_LONG).show()
                }
            }
        }

            viewModel.mailIsValid.observe(viewLifecycleOwner) { areValid ->
                areValid?.let {
                    if (it) {
                    } else {
                        Toast.makeText(context, "Mail no registrado", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }



    private fun register(){
        val registerButton = binding.RegisterButton

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginAccessFragment_to_registerAccess01Fragment)
        }
    }
}



