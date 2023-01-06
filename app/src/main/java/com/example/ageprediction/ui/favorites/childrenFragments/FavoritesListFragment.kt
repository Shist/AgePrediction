package com.example.ageprediction.ui.favorites.childrenFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ageprediction.databinding.FragmentFavoritesListBinding
import com.example.ageprediction.databinding.CustomDialogDeleteBinding
import com.example.ageprediction.ui.favorites.childrenFragments.adapter.NameItem
import com.example.ageprediction.ui.favorites.childrenFragments.adapter.NamesAdapter
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class FavoritesListFragment : Fragment(), KoinComponent  {

    private var _binding: FragmentFavoritesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FavouriteItemViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favItemsFlow.collect { favItems ->
                    if (favItems != null) { // <-- That value can be null if there are some problems at DB
                        if (favItems.isNotEmpty()) {
                            binding.textEmptyList.visibility = View.GONE

                            val recyclerView = binding.recyclerView
                            recyclerView.layoutManager = LinearLayoutManager(context)

                            val adapter = NamesAdapter({ showButton() }, { hideButton() })
                            recyclerView.adapter = adapter

                            val mutableListNamesItems = mutableListOf<NameItem>()
                            for (favItem in favItems) {
                                mutableListNamesItems.add(NameItem(favItem.name, false))
                            }

                            adapter.submitList(mutableListNamesItems)

                            binding.btnDelete.setOnClickListener {
                                val dialogBinding =
                                    CustomDialogDeleteBinding.inflate(LayoutInflater.from(it.context), null, false)
                                val dialog = AlertDialog.Builder(it.context)
                                    .setCancelable(false)
                                    .setView(dialogBinding.root)
                                    .show()
                                dialogBinding.btnNo.setOnClickListener {
                                    dialog.dismiss()
                                }
                                dialogBinding.btnYes.setOnClickListener {
                                    val namesItems = adapter.currentList
                                    val newListNamesItems = mutableListOf<NameItem>()
                                    val itemsToBeDeleted = mutableListOf<String>()

                                    for (item in namesItems) {
                                        if (item.checkBoxState) {
                                            itemsToBeDeleted.add(item.textName)
                                        } else {
                                            newListNamesItems.add(item)
                                        }
                                    }

                                    viewModel.deleteFavItemsFromDB(itemsToBeDeleted)

                                    if (newListNamesItems.isEmpty()) {
                                        binding.textEmptyList.visibility = View.VISIBLE
                                    } else {
                                        binding.textEmptyList.visibility = View.GONE
                                    }

                                    adapter.submitList(newListNamesItems)
                                    adapter.currentAmountOfCheckedBoxes = 0
                                    hideButton()
                                    dialog.dismiss()
                                }
                            }
                        } else {
                            binding.textEmptyList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
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