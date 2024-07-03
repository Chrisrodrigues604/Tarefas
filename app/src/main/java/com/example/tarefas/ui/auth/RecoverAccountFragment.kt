package com.example.tarefas.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.tarefas.databinding.FragmentRecoverAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {

        binding.btnSend.setOnClickListener { validateData() }

    }

    private fun validateData() {

        val email = binding.edtEmail.text.toString().trim()

        if(email.isNotEmpty()) {
            binding.progressbar.isVisible = true
            recoverAccount(email)
        } else {
            Toast.makeText(requireContext(), "informe seu email.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun recoverAccount(email:String){
        auth.sendPasswordResetEmail (email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Link de recuoeração enviado para o e-mail informado.", Toast.LENGTH_SHORT).show()
                }
                //else {
                    binding.progressbar.isVisible = false
                //}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}