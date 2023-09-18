package com.rubylichtenstein.domain.common

import java.util.Locale

fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.ROOT
    ) else it.toString()
}

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.capitalize() }
