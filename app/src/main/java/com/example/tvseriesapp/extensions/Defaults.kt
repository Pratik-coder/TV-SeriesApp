package com.example.tvseriesapp.extensions

const val DEFAULT_INT=Int.MIN_VALUE
const val DEFAULT_LONG=Long.MIN_VALUE
const val DEFAULT_DOUBLE=-10000.1

fun Int?.orDefault():Int= this?: DEFAULT_INT

fun Long?.orDefault():Long= this?: DEFAULT_LONG

fun Double?.orDefault():Double= this?: DEFAULT_DOUBLE

fun Boolean?.orFalse():Boolean = this?:false

fun String?.orNa():String{
    return if (isNullOrEmpty()) "N/A" else this
}




