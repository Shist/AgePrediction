package com.example.ageprediction

object Consts {
    const val BASE_URL = "https://api.agify.io"

    enum class NameErrorType {
        EMPTY,
        SMALL_LETTER,
        CONTAINS_NOT_LETTERS
    }

    const val KEY_QUERY_NAME = "queryName"

    const val KEY_ERROR_TYPE = "errorType"
}