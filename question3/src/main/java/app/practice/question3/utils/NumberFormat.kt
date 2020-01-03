package app.practice.question3.utils

import java.text.DecimalFormat


val decFormat = DecimalFormat("#.#")

fun String?.toDecimalFormat(): String {
    try {
        val number = this?.toInt()
        return decFormat.format(number)

    } catch (e: Exception) {
        return "0"
    }
}