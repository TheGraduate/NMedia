package ru.netology.nmedia


fun calculateParametrs (value: Int): String {
    var counter = "0"
    if (value >= 1_000_000 ){
        counter = "${value/1_000_000}" + ".${value % 1_000_000 / 100_000}" + "M"
    } else if (value >= 10000) {
        counter = "${value/1000}" + "K"
    } else if (value >= 1000) {
        counter = "${value/1000}" + ".${value % 1000 / 100}" + "K"
    } else if (value < 1000) {
        counter = "${value}"
    }
    return counter
}