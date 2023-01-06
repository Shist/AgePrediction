package com.example.ageprediction.ui.home.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.example.ageprediction.databinding.SearchViewListItemBinding

class SearchHistoryAdapter(context: Context, c: Cursor?, autoRequery: Boolean)
    : CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context, cursor: Cursor?, parent: ViewGroup?): View {
        val binding = SearchViewListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.root.tag = binding
        return binding.root
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val binding = view.tag as SearchViewListItemBinding

        val index = cursor.getColumnIndex("display1")
        if (index >= 0) {
            val text = cursor.getString(index)
            binding.text.text = text
        }
    }
}