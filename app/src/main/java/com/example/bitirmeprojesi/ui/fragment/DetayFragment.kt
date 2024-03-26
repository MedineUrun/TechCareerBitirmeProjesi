package com.example.bitirmeprojesi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentDetayBinding
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.example.bitirmeprojesi.utils.gecis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {

    private lateinit var binding: FragmentDetayBinding
    private lateinit var viewModel: DetayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetayBinding.inflate(inflater, container, false)
        viewModel.sepettekiYemekleriYukle()

        val bundle: DetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek
        var adet = 1

        binding.tvYemekAdi.setText(gelenYemek.yemek_adi)
        binding.tvFiyat.setText("₺" + gelenYemek.yemek_fiyat.toString())
        binding.tvDetayFiyat.setText("₺" + gelenYemek.yemek_fiyat.toString())

        val resimURL = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Glide.with(requireContext())
            .load(resimURL)
            .into(binding.ivYemekResmi)

        binding.ivAzalt.setOnClickListener {
            if (adet > 1) {
                adet--
                binding.tvAdet.setText(adet.toString())
                binding.tvDetayFiyat.setText("₺" + (gelenYemek.yemek_fiyat * adet).toString())
            }
        }
        binding.ivArtir.setOnClickListener {
            adet++
            binding.tvAdet.setText(adet.toString())
            binding.tvDetayFiyat.setText("₺" + (gelenYemek.yemek_fiyat * adet).toString())
        }

        binding.btnEkle.setOnClickListener {
            val yemek_adi = gelenYemek.yemek_adi
            val yemek_resim_adi = gelenYemek.yemek_resim_adi
            val yemek_fiyat = gelenYemek.yemek_fiyat
            val kullanici_adi = "medine_bozkurt"

            val sepetYemeklerListesi = viewModel.sepetYemeklerListesi.value

            if (sepetYemeklerListesi.isNullOrEmpty()) {
                viewModel.sepeteYemekEkle(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    adet,
                    kullanici_adi
                )
            } else {
                var yemekEklendi = false
                for (item in sepetYemeklerListesi) {
                    if (item.yemek_adi == gelenYemek.yemek_adi) {
                        adet += item.yemek_siparis_adet
                        viewModel.sepettekiYemekleriSil(item.sepet_yemek_id, item.kullanici_adi)

                        viewModel.sepeteYemekEkle(
                            yemek_adi,
                            yemek_resim_adi,
                            yemek_fiyat,
                            adet,
                            kullanici_adi
                        )
                        yemekEklendi = true
                        break
                    }
                }

                if (!yemekEklendi) {
                    viewModel.sepeteYemekEkle(
                        yemek_adi,
                        yemek_resim_adi,
                        yemek_fiyat,
                        adet,
                        kullanici_adi
                    )
                }
            }
            Navigation.gecis(it, R.id.detay_sepet_gecis)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetayViewModel by viewModels()
        viewModel = tempViewModel
    }
}