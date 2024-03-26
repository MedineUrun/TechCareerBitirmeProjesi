package com.example.bitirmeprojesi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SepetViewModel @Inject constructor(var yrepo: YemeklerRepository) : ViewModel() {

    var sepetYemeklerListesi = MutableLiveData<List<SepetYemekler>>()

    init {
        sepettekiYemekleriYukle()
    }

    fun sepettekiYemekleriSil(sepet_yemek_id: Int, kullanici_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sepettekiYemekleriSil(sepet_yemek_id, kullanici_adi)
            sepettekiYemekleriYukle()
        }
    }

    fun sepettekiYemekleriYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            sepetYemeklerListesi.value = yrepo.sepettekiYemekleriGetir()
        }

    }
}
