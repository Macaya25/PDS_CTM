package cl.uandes.pichangapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.RegisterAccess01FragmentBinding
import cl.uandes.pichangapp.data.model.FakeData.Users
import cl.uandes.pichangapp.ui.viewmodel.SignUpViewModel

class RegisterAccess01Fragment : Fragment() {
    private lateinit var binding: RegisterAccess01FragmentBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_access01_fragment,
            container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume(){
        super.onResume()
        val category = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        binding.autoCompleteTextView3.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpViewModel = viewModel
        //menu()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.registerAreValid.observe(viewLifecycleOwner) { areValid ->
            areValid?.let {
                if (it) {
                    viewModel.completeIsValid.observe(viewLifecycleOwner) { areValid ->
                        areValid?.let {
                            if (it){
                                viewModel.passwordLenghtValid.observe(viewLifecycleOwner) { areValid ->
                                    areValid?.let {
                                        if (it){
                                            viewModel.credentialsAreValid.observe(viewLifecycleOwner) { areValid ->
                                                areValid?.let {
                                                    if (it) {
                                                        findNavController().navigate(R.id.action_registerAccess01Fragment_to_search_match_navigation)
                                                    } else {
                                                        Toast.makeText(context, "No se pudo iniciar sesión en la app", Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                            }

                                        }
                                        else {
                                            Toast.makeText(context, "Password tiene que tener 6 o más caracteres", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }


                            }
                            else{
                                Toast.makeText(context, "Falta llenar campos", Toast.LENGTH_LONG).show()
                            }
                        }
                    }


                } else {
                    Toast.makeText(context, "Mail ya esta en uso", Toast.LENGTH_LONG).show()
                }
            }
        }
    }



/*


 viewModel.credentialsAreValid.observe(viewLifecycleOwner) { areValid ->
                        areValid?.let {
                            if (it) {
                                findNavController().navigate(R.id.action_registerAccess01Fragment_to_search_match_navigation)
                            } else {
                                Toast.makeText(context, "No se pudo iniciar sesión en la app", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
    private fun menu(){
        val Button = binding.DoneButton
        val editTextMail = binding.editTextMail
        val editTextPassword = binding.editTextPassword
        val editTextTeamName = binding.editTextTeamName
        val editTextCategory = binding.editTextCategory


        Button.setOnClickListener {
            val arg1 = editTextMail.text.toString()
            val arg2 = editTextPassword.text.toString()
            val arg3 = editTextTeamName.text.toString()
            val arg4 = editTextCategory.text.toString()

            if (newUser(arg1)){
                currentUser =  arg1
                teamName = arg3
                category = arg4
                if (currentUser== ""|| teamName==""||category==""){
                    Toast.makeText(context, "Campos Vacíos", Toast.LENGTH_LONG).show()
                }
                else if (category!= "Femenino" && category!= "Masculino" && category!= "Mixto"){
                    Toast.makeText(context, "Categoría debe ser: Femenino Masculino o Mixto", Toast.LENGTH_LONG).show()
                }
                else {
                    Users.createUser(arg1, arg2, arg3, arg4, R.drawable.extra_logo_chelsea)
                    findNavController().navigate(R.id.action_registerAccess01Fragment_to_search_match_navigation)
                }
            }
            else {
                Toast.makeText(context, "Mail ya registrado", Toast.LENGTH_LONG).show()

            }


        }
    }

    private fun newUser(userMail: String ): Boolean{
        allUsers.find{it.mail==userMail} ?: return true
        return false
    }

 */
}