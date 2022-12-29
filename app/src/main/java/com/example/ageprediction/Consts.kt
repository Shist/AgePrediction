package com.example.ageprediction

object Consts {
    const val BASE_URL = "https://api.agify.io"

    enum class NameErrorType {
        EMPTY,
        SMALL_LETTER,
        CONTAINS_NOT_LETTERS,
        NO_RESULT
    }

    const val FAVORITE_NAMES_PREFERENCES = "FAVORITE_NAMES"
    const val FAVORITE_NAMES_PREFERENCES_WHOLE_STRING = "FAVORITE_NAMES_WHOLE_STRING"
    const val FAVORITE_NAMES_PREFERENCES_SPLITTER = "||||"
}