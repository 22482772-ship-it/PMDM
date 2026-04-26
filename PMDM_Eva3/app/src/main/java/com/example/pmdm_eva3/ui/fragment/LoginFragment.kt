package com.example.pmdm_eva3.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pmdm_eva3.R
import com.example.pmdm_eva3.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

// Fragment de inicio de sesión, usa Firebase Authentication para autenticar al usuario
class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registrarButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                binding.emailText.text.toString(),
                binding.passwordText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(binding.root, "Usuario creado con éxito", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "No se pudo crear el usuario", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.emailText.text.toString(),
                binding.passwordText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(binding.root, "Bienvenido", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_MainFragment)
                } else {
                    Snackbar.make(binding.root, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
