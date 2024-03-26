package com.example.bitirmeprojesi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.Yemekler
import com.example.bitirmeprojesi.databinding.CardTasarimBinding
import com.example.bitirmeprojesi.ui.fragment.AnasayfaFragmentDirections
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.example.bitirmeprojesi.utils.gecis
import com.google.android.material.snackbar.Snackbar

class YemeklerAdapter(
    var mContext: Context,
    var yemeklerListesi: List<Yemekler>,
    var viewModel: AnasayfaViewModel
) : RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>() {
    inner class CardTasarimTutucu(var tasarim: CardTasarimBinding) :
        RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val binding = CardTasarimBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val t = holder.tasarim
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        t.tvYemekIsim.text = yemek.yemek_adi
        Glide.with(mContext).load(url).into(t.ivYemek)
        t.tvYemekUcreti.text = "₺" + yemek.yemek_fiyat.toString()

        t.cardViewYemek.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.yemekDetayGecis(yemek = yemek)
            Navigation.gecis(it, gecis)
        }

        t.ivAnasayfaSepetEkle.setOnClickListener {
            val yemek_adi = t.tvYemekIsim.text.toString()
            val yemek_resim_adi =
                yemeklerListesi.find { it.yemek_adi == yemek_adi }!!.yemek_resim_adi
            val yemek_fiyat = yemeklerListesi.find { it.yemek_adi == yemek_adi }!!.yemek_fiyat
            var adet = 1
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
                    if (item.yemek_adi == yemek_adi) {
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
            Snackbar.make(it, "1 adet ${yemek_adi} sepete eklendi!", Snackbar.LENGTH_SHORT).show()
        }
    }
}