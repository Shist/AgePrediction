package com.example.ageprediction.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentFavoritesBinding
import com.example.ageprediction.ui.favorites.childrenFragments.FavoritesEmptyFragment
import com.example.ageprediction.ui.favorites.childrenFragments.FavoritesListFragment
import com.example.ageprediction.ui.favorites.childrenFragments.adapter.NameItem

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favNamesPrefs = context?.getSharedPreferences(Consts.FAVORITE_NAMES_PREFERENCES, Context.MODE_PRIVATE)
        val favNamesWholeString = favNamesPrefs?.getString(Consts.FAVORITE_NAMES_PREFERENCES_WHOLE_STRING, "")
        if (favNamesWholeString != null && favNamesWholeString != "") {
            val favNamesData = favNamesWholeString.split(Consts.FAVORITE_NAMES_PREFERENCES_SPLITTER)
            val mutableListNamesItems = mutableListOf<NameItem>()
            for (name in favNamesData) {
                mutableListNamesItems.add(NameItem(name, false))
            }
            childFragmentManager
                .beginTransaction()
                .replace(R.id.favorites_fragment_container,
                    FavoritesListFragment(mutableListNamesItems) { setEmptyFragment() })
                .commit()
        } else {
            setEmptyFragment()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEmptyFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.favorites_fragment_container, FavoritesEmptyFragment())
            .commit()
    }
}