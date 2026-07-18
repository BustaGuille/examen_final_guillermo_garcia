package com.guillermogarcia.examenfinal2026

import java.util.UUID

data class Empleado(
    val id: String = UUID.randomUUID().toString(),
    val nombreCompleto: String,
    val cargo: String,
    val departamento: String,
    val salario: Double,
    val fechaContratacion: String
)
