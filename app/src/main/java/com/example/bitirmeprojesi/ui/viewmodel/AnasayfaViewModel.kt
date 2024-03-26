package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yrepo: YemeklerRepository) : ViewModel() {

    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var sepetYemeklerListesi = MutableLiveData<List<SepetYemekler>>()

    init {
        yemekleriYukle()
        sepettekiYemekleriYukle()
    }

    fun yemekleriYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yrepo.yemekleriYukle()
        }
    }

    fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sepeteYemekEkle(
                yemek_adi,
                yemek_resim_adi,
                yemek_fiyat,
                yemek_siparis_adet,
                kullanici_adi
            )
        }
    }

    fun sepettekiYemekleriYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            sepetYemeklerListesi.value = yrepo.sepettekiYemekleriGetir()
        }
    }

    fun sepettekiYemekleriSil(sepet_yemek_id: Int, kullanici_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sepettekiYemekleriSil(sepet_yemek_id, kullanici_adi)
            sepettekiYemekleriYukle()
        }
    }

    fun yemekGetir(aranacakKelime: String) {
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yemeklerListesi.value!!.filter {
                it.yemek_adi.contains(
                    aranacakKelime,
                    ignoreCase = true
                )
            }
        }
    }
}