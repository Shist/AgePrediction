package com.example.ageprediction

import android.content.SearchRecentSuggestionsProvider

class SearchSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.ageprediction.SearchSuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}