package ru.netology.nmedia

fun calculateParameters(value: Int): String {
    val formattedValue = when {
        value >= 1_000_000 -> "${value/1_000_000}.${value % 1_000_000 / 100_000}M"
        value >= 1_0000 -> "${value/1000}" + "K"
        value >= 1_000 -> "${value/1_000}.${value % 1_000 / 100}K"
        else -> value.toString()
    }
    return formattedValue
}