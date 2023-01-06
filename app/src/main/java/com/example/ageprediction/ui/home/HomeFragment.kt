package com.example.ageprediction.ui.home

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.SearchSuggestionProvider
import com.example.ageprediction.databinding.FragmentHomeBinding
import com.example.ageprediction.ui.home.adapter.SearchHistoryAdapter
import com.example.ageprediction.ui.home.childrenFragments.HomeEmptyFragment
import com.example.ageprediction.ui.home.childrenFragments.HomeNoResultFragment
import com.example.ageprediction.ui.home.childrenFragments.HomeSuccessFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun String.onlyLetters() = all { it.isLetter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchLine.isActivated = true
        binding.searchLine.queryHint = resources.getString(R.string.text_search)
        binding.searchLine.onActionViewExpanded()
        binding.searchLine.isIconified = false
        binding.searchLine.clearFocus()

        // Get the SearchView and set the searchable configuration
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (binding.searchLine).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            isIconifiedByDefault = false // Do not iconify the widget; expand it by default
        }

        val adapter = SearchHistoryAdapter(requireContext(), null, true)
        binding.searchLine.suggestionsAdapter = adapter

        binding.searchLine.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                val queryString = query.trim()
                SearchRecentSuggestions(requireContext(), SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                if (queryString.isEmpty()) {
                    val fragment = HomeNoResultFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable(Consts.KEY_ERROR_TYPE, Consts.NameErrorType.EMPTY)
                        }
                    }
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_fragment_container, fragment)
                        .commit()
                } else {
                    if (!queryString[0].isUpperCase()) {
                        val fragment = HomeNoResultFragment().apply {
                            arguments = Bundle().apply {
                                putSerializable(Consts.KEY_ERROR_TYPE, Consts.NameErrorType.SMALL_LETTER)
                            }
                        }
                        childFragmentManager
                            .beginTransaction()
                            .replace(R.id.home_fragment_container, fragment)
                            .commit()
                    } else {
                        if (!queryString.onlyLetters()) {
                            val fragment = HomeNoResultFragment().apply {
                                arguments = Bundle().apply {
                                    putSerializable(Consts.KEY_ERROR_TYPE, Consts.NameErrorType.CONTAINS_NOT_LETTERS)
                                }
                            }
                            childFragmentManager
                                .beginTransaction()
                                .replace(R.id.home_fragment_container, fragment)
                                .commit()
                        } else {
                            val fragment = HomeSuccessFragment().apply {
                                arguments = Bundle().apply {
                                    putString(Consts.KEY_QUERY_NAME, query)
                                }
                            }
                            childFragmentManager
                                .beginTransaction()
                                .replace(R.id.home_fragment_container, fragment)
                                .commit()
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val cursor = requireContext().contentResolver.query(
                    Uri.parse("content://${SearchSuggestionProvider.AUTHORITY}/suggestions"),
                    null,
                    bundleOf(
                        "query" to newText
                    ),
                    null,
                )
                adapter.changeCursor(cursor)
                return false
            }
        })

        binding.searchLine.setOnSuggestionListener(object : SearchView.OnSuggestionListener {

            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = adapter.getItem(position) as Cursor
                val index = cursor.getColumnIndex("display1")
                if (index < 0) {
                    return false
                }
                val selection = cursor.getString(index)
                binding.searchLine.setQuery(selection, true)
                cursor.close()
                return true
            }

        })

        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_fragment_container, HomeEmptyFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}