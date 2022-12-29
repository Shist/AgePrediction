package com.example.ageprediction.ui.favorites.childrenFragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentFavoritesListBinding
import com.example.ageprediction.ui.favorites.childrenFragments.adapter.NameItem
import com.example.ageprediction.ui.favorites.childrenFragments.adapter.NamesAdapter

class FavoritesListFragment(private val listOfNames : MutableList<NameItem>,
                            private val setEmptyFragmentFun: ()-> Unit) : Fragment()  {

    private var _binding: FragmentFavoritesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = NamesAdapter({ showButton() }, { hideButton() })
        recyclerView.adapter = adapter
        adapter.submitList(listOfNames)

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(getString(R.string.title_delete_name))
                setMessage(getString(R.string.text_delete_name))
                setPositiveButton(getString(R.string.text_yes)) { _, _ ->
                    val namesItems = adapter.currentList
                    val newListNamesItems = mutableListOf<NameItem>()

                    var stringForPreferences = ""
                    var splitterIsNeeded = false
                    for (item in namesItems) {
                        if (!item.checkBoxState) {
                            newListNamesItems.add(item)

                            if (splitterIsNeeded) {
                                stringForPreferences += Consts.FAVORITE_NAMES_PREFERENCES_SPLITTER
                            }
                            stringForPreferences += item.textName
                            splitterIsNeeded = true
                        }
                    }

                    val favNamesPrefs = context?.getSharedPreferences(Consts.FAVORITE_NAMES_PREFERENCES, Context.MODE_PRIVATE)
                    val favNamesEditor = favNamesPrefs?.edit()
                    favNamesEditor?.putString(Consts.FAVORITE_NAMES_PREFERENCES_WHOLE_STRING, stringForPreferences)
                    favNamesEditor?.apply()

                    if (newListNamesItems.isEmpty()) {
                        setEmptyFragmentFun()
                    } else {
                        adapter.submitList(newListNamesItems)
                        adapter.currentAmountOfCheckedBoxes = 0
                        hideButton()
                    }
                }
                setNegativeButton(getString(R.string.text_no)) { _, _ -> }
                create()
            }.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showButton() {
        binding.btnDelete.visibility = View.VISIBLE
    }

    private fun hideButton() {
        binding.btnDelete.visibility = View.GONE
    }
}