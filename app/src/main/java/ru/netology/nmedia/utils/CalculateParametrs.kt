package ru.netology.nmedia

/*fun calculateParametrs (value: Int): String {
    var counter = "0"
    when {
        value >= 1_000_000 -> {
            counter = "${value/1_000_000}" + ".${value % 1_000_000 / 100_000}" + "M"
        }
        value >= 10000 -> {
             counter = "${value/1000}" + "K"
        }
        value >= 1000 -> {
            counter = "${value/1000}" + ".${value % 1000 / 100}" + "K"
        }
        value < 1000 -> {
            counter = "$value"
        }

    }
    return counter
}*/
fun calculateParameters(value: Int): String {
    val formattedValue = when {
        value >= 1_000_000 -> "${value/1_000_000}.${value % 1_000_000 / 100_000}M"
        value >= 1_0000 -> "${value/1000}" + "K"
        value >= 1_000 -> "${value/1_000}.${value % 1_000 / 100}K"
        else -> value.toString()
    }
    return formattedValue
}