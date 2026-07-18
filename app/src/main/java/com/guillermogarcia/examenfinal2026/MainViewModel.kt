package com.guillermogarcia.examenfinal2026

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _empleados = mutableStateListOf<Empleado>()
    val empleados: List<Empleado> = _empleados

    fun agregarEmpleado(empleado: Empleado) {
        _empleados.add(empleado)
    }

    fun eliminarEmpleado(empleado: Empleado) {
        _empleados.remove(empleado)
    }
}
