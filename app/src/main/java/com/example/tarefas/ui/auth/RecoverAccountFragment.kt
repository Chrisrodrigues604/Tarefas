package com.example.tarefas.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.tarefas.R
import com.example.tarefas.databinding.FragmentRecoverAccountBinding
import com.example.tarefas.helper.BaseFragment
import com.example.tarefas.helper.FirebaseHelper
import com.example.tarefas.helper.initToolbar
import com.example.tarefas.helper.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {

        binding.btnSend.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        if (email.isNotEmpty()) {
            hideKeyboard()
            binding.progressBar.isVisible = true
            recoverAccountUser(email)
        } else {
            showBottomSheet(
                message = R.string.text_email_empty_recover_fragment
            )
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showBottomSheet(
                        message = R.string.text_confirmation_send_email_recover_fragment
                    )
                }else{
                    showBottomSheet(
                        message = FirebaseHelper.validError(task.exception?.message ?: "")
                    )
                }
                binding.progressBar.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}