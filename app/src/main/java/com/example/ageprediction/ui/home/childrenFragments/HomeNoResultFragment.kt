package com.example.ageprediction.ui.home.childrenFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ageprediction.Consts
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentHomeNoResultBinding

class HomeNoResultFragment(private val errorType: Consts.NameErrorType) : Fragment()  {

    private var _binding: FragmentHomeNoResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeNoResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        when(errorType) {
            Consts.NameErrorType.EMPTY -> {
                binding.textNoResult.text = resources.getString(R.string.text_error_name_empty)
            }
            Consts.NameErrorType.SMALL_LETTER -> {
                binding.textNoResult.text = resources.getString(R.string.text_error_name_with_small_letter)
            }
            Consts.NameErrorType.CONTAINS_NOT_LETTERS -> {
                binding.textNoResult.text = resources.getString(R.string.text_error_name_contains_not_letters)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}