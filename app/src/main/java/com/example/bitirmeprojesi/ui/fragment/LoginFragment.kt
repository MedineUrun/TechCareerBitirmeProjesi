package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentLoginBinding
import com.example.bitirmeprojesi.utils.gecis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            Navigation.gecis(it, R.id.login_anasayfa_gecis)
        }
        return binding.root
    }
}