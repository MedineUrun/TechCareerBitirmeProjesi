package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentAnasayfaBinding
import com.example.bitirmeprojesi.ui.adapter.YemeklerAdapter
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.example.bitirmeprojesi.utils.gecis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)

        binding.progressBar.visibility = View.VISIBLE
        viewModel.yemeklerListesi.observe(viewLifecycleOwner) {
            val yemeklerAdapter = YemeklerAdapter(requireContext(), it, viewModel)
            binding.yemeklerRv.adapter = yemeklerAdapter
            binding.progressBar.visibility = View.GONE
        }

        binding.yemeklerRv.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

        binding.fabSepet.setOnClickListener {
            Navigation.gecis(it, R.id.anasayfa_sepet_gecis)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(aranacakKelime: String): Boolean {
                if (aranacakKelime.isEmpty()) {
                    viewModel.yemekleriYukle()
                } else {
                    viewModel.yemekGetir(aranacakKelime)
                }
                return true
            }

            override fun onQueryTextSubmit(aranacakKelime: String): Boolean {
                return true
            }
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.yemekleriYukle()
        viewModel.sepettekiYemekleriYukle()
    }
}