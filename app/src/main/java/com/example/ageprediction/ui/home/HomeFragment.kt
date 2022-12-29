package com.example.ageprediction.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentHomeBinding
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
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        binding.searchLine.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                val queryString = binding.searchLine.query.toString()

                if (queryString.isEmpty()) {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_fragment_container, HomeNoResultFragment(Consts.NameErrorType.EMPTY))
                        .commit()
                } else {
                    if (!queryString[0].isUpperCase()) {
                        childFragmentManager
                            .beginTransaction()
                            .replace(R.id.home_fragment_container, HomeNoResultFragment(Consts.NameErrorType.SMALL_LETTER))
                            .commit()
                    } else {
                        if (!queryString.onlyLetters()) {
                            childFragmentManager
                                .beginTransaction()
                                .replace(R.id.home_fragment_container, HomeNoResultFragment(Consts.NameErrorType.CONTAINS_NOT_LETTERS))
                                .commit()
                        } else {
                            homeViewModel.getAge(queryString)
                            val ageObserver = Observer<String> { newAge ->
                                if (newAge == "null") {
                                    childFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.home_fragment_container, HomeNoResultFragment(Consts.NameErrorType.NO_RESULT))
                                        .commit()
                                } else {
                                    childFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.home_fragment_container, HomeSuccessFragment(queryString, newAge))
                                        .commit()
                                }
                            }
                            homeViewModel.currAge.observe(viewLifecycleOwner, ageObserver)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // We can do something while text is changing
                return false
            }
        })

        childFragmentManager
            .beginTransaction()
            .replace(R.id.home_fragment_container, HomeEmptyFragment())
            .commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}