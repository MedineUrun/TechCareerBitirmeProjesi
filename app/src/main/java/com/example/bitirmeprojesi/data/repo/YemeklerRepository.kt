package com.example.bitirmeprojesi.data.repo

import com.example.bitirmeprojesi.data.datasource.YemeklerDataSource
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.data.entity.Yemekler

class YemeklerRepository(var yds: YemeklerDataSource) {

    suspend fun sepettekiYemekleriSil(
        sepet_yemek_id: Int,
        kullanici_adi: String
    ) = yds.sepettekiYemekleriSil(sepet_yemek_id, kullanici_adi)

    suspend fun yemekleriYukle(): List<Yemekler> = yds.yemekleriYukle()

    suspend fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) = yds.sepeteYemekEkle(
        yemek_adi,
        yemek_resim_adi,
        yemek_fiyat,
        yemek_siparis_adet,
        kullanici_adi
    )

    suspend fun sepettekiYemekleriGetir(): List<SepetYemekler> = yds.sepettekiYemekleriGetir()
}