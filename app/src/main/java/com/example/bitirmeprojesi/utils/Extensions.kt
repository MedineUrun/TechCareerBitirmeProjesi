package com.example.bitirmeprojesi.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.gecis(it: View, id:Int){
    findNavController(it).navigate(id)
}

fun Navigation.gecis(it: View, id: NavDirections){
    findNavController(it).navigate(id)
}