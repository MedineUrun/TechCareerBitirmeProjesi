package com.example.bitirmeprojesi.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.databinding.FragmentSepetBinding
import com.example.bitirmeprojesi.ui.adapter.SepetYemeklerAdapter
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSepetBinding.inflate(inflater, container, false)

        binding.progressBar.visibility = View.VISIBLE
        viewModel.sepetYemeklerListesi.observe(viewLifecycleOwner) { sepetYemekler ->

            if (sepetYemekler.isEmpty()) {
                binding.ivSepetBos.visibility = View.VISIBLE
                binding.rvSepetYemekler.visibility = View.GONE
            } else {
                binding.ivSepetBos.visibility = View.GONE
                binding.rvSepetYemekler.visibility = View.VISIBLE

                val sepetYemeklerAdapter =
                    SepetYemeklerAdapter(requireContext(), sepetYemekler, viewModel)
                binding.rvSepetYemekler.adapter = sepetYemeklerAdapter

                binding.rvSepetYemekler.layoutManager = LinearLayoutManager(requireContext())
            }

            var toplamFiyat = 0
            sepetYemekler.forEach {
                toplamFiyat += it.yemek_fiyat * it.yemek_siparis_adet
            }
            binding.tvToplamFiyat.text = "₺$toplamFiyat"

            binding.progressBar.visibility = View.GONE
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriYukle()
    }
}