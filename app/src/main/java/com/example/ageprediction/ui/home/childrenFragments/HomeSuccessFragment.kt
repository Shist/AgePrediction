package com.example.ageprediction.ui.home.childrenFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentHomeSuccessBinding
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class HomeSuccessFragment : Fragment(), KoinComponent  {

    private var _binding: FragmentHomeSuccessBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var currName = ""

    private val viewModel: SearchItemViewModel by inject {
        currName = arguments?.getString(Consts.KEY_QUERY_NAME).toString()
        parametersOf(currName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSearchItemToDB(currName)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchItemFlow.collect { searchItem ->
                    if (searchItem != null) { // <-- That value can be null in cases when we're adding new value to DB
                        if (searchItem.age != null) {
                            binding.textAge.text = searchItem.age

                            binding.btnAddToFavorites.visibility = View.VISIBLE
                            binding.btnAddToFavorites.setOnClickListener {
                                viewModel.loadFavItemToDB(currName)
                            }

                            binding.btnShare.visibility = View.VISIBLE
                            binding.btnShare.setOnClickListener {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        getString(R.string.text_share_with, searchItem.name, searchItem.age)
                                    )
                                    type = "text/plain"
                                }
                                startActivity(Intent.createChooser(intent, getString(R.string.label_share_with)))
                            }
                        } else {
                            binding.textAge.text = resources.getString(R.string.text_no_result)
                            binding.btnAddToFavorites.visibility = View.INVISIBLE
                            binding.btnShare.visibility = View.INVISIBLE
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
}