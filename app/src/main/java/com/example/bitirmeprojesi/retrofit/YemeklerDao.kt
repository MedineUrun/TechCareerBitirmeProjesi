package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.data.entity.SepetYemekEkleCevap
import com.example.bitirmeprojesi.data.entity.SepetYemekGetirCevap
import com.example.bitirmeprojesi.data.entity.SepetYemekSilCevap
import com.example.bitirmeprojesi.data.entity.YemeklerCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle(): YemeklerCevap

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): SepetYemekEkleCevap

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun sepettekiYemekleriGetir(@Field("kullanici_adi") kullanici_adi: String): SepetYemekGetirCevap

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun sepettekiYemekleriSil(
        @Field("sepet_yemek_id") sepet_yemek_id: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): SepetYemekSilCevap


}