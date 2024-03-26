package com.example.bitirmeprojesi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesi.data.entity.SepetYemekler
import com.example.bitirmeprojesi.databinding.SepetCardTasarimBinding
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import com.google.android.material.snackbar.Snackbar

class SepetYemeklerAdapter(
    var mContext: Context,
    var sepetYemeklerListesi: List<SepetYemekler>,
    var viewModel: SepetViewModel
) : RecyclerView.Adapter<SepetYemeklerAdapter.CardTasarimTutucu>() {
    inner class CardTasarimTutucu(var tasarim: SepetCardTasarimBinding) :
        RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val binding = SepetCardTasarimBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }

    override fun getItemCount(): Int {
        return sepetYemeklerListesi.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val sepetYemek = sepetYemeklerListesi.get(position)
        val t = holder.tasarim
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).into(t.ivSepetYemek)
        t.tvSepetYemekAdi.text = sepetYemek.yemek_adi
        t.tvSepetYemekFiyat.text = "₺" + sepetYemek.yemek_fiyat.toString()
        t.tvSepetYemekAdet.text = sepetYemek.yemek_siparis_adet.toString()

        t.ivSil.setOnClickListener {
            Snackbar.make(it, "${sepetYemek.yemek_adi} silinsin mi?", Snackbar.LENGTH_SHORT)
                .setAction("EVET") {
                    viewModel.sepettekiYemekleriSil(
                        sepetYemek.sepet_yemek_id,
                        sepetYemek.kullanici_adi
                    )
                }.show()
        }

        t.tvYemekToplamFiyat.text =
            "₺" + (sepetYemek.yemek_fiyat * sepetYemek.yemek_siparis_adet).toString()
    }
}