package com.example.bitirmeprojesi.data.datasource

import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var ydao: YemeklerDao) {

    suspend fun sepettekiYemekleriSil(
        sepet_yemek_id: Int,
        kullanici_adi: String
    ) = ydao.sepettekiYemekleriSil(sepet_yemek_id, kullanici_adi)

    suspend fun yemekleriYukle(): List<Yemekler> =
        withContext(Dispatchers.IO) {
            return@withContext ydao.yemekleriYukle().yemekler
        }

    suspend fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) = ydao.sepeteYemekEkle(
        yemek_adi,
        yemek_resim_adi,
        yemek_fiyat,
        yemek_siparis_adet,
        kullanici_adi
    )

    suspend fun sepettekiYemekleriGetir(): List<SepetYemekler> =
        withContext(Dispatchers.IO) {
            try {
                val response = ydao.sepettekiYemekleriGetir("medine_bozkurt")
                return@withContext response.sepet_yemekler
            } catch (exception: Exception) {
                println("Hata: $exception")
                return@withContext emptyList()
            }
        }
}