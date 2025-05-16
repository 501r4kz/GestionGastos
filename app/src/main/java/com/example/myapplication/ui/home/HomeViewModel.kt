package com.example.myapplication.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*

class HomeViewModel : ViewModel() {
    private val _ingresos = mutableStateOf(0)
    val ingresos: State<Int> = _ingresos

    private val _gastos = mutableStateListOf<Pair<String, Int>>()
    val gastos: List<Pair<String, Int>> = _gastos

    fun agregarIngreso(monto: Int) {
        _ingresos.value += monto
    }

    fun agregarGasto(nombre: String, cantidad: Int, descripcion: String) {
        _gastos.add(Pair(nombre, cantidad))
    }
}

