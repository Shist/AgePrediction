package com.example.ageprediction.ui.home.childrenFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ageprediction.Consts.FAVORITE_NAMES_PREFERENCES
import com.example.ageprediction.Consts.FAVORITE_NAMES_PREFERENCES_SPLITTER
import com.example.ageprediction.Consts.FAVORITE_NAMES_PREFERENCES_WHOLE_STRING
import com.example.ageprediction.R
import com.example.ageprediction.databinding.FragmentHomeSuccessBinding

class HomeSuccessFragment(private val name : String, private val age : String) : Fragment()  {

    private var _binding: FragmentHomeSuccessBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeSuccessBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textAge.text = age

        binding.btnAddToFavorites.setOnClickListener {
            val favNamesPrefs = context?.getSharedPreferences(FAVORITE_NAMES_PREFERENCES, Context.MODE_PRIVATE)
            val favNamesWholeString = favNamesPrefs?.getString(
                FAVORITE_NAMES_PREFERENCES_WHOLE_STRING, "")
            val favNamesEditor = favNamesPrefs?.edit()
            if (favNamesWholeString == "") {
                favNamesEditor?.putString(FAVORITE_NAMES_PREFERENCES_WHOLE_STRING, name)
            } else {
                val favNamesData = favNamesWholeString?.split(FAVORITE_NAMES_PREFERENCES_SPLITTER)
                if (favNamesData != null && !favNamesData.contains(name)) {
                    favNamesEditor?.putString(FAVORITE_NAMES_PREFERENCES_WHOLE_STRING,
                        favNamesWholeString + FAVORITE_NAMES_PREFERENCES_SPLITTER + name)
                }
            }
            favNamesEditor?.apply()
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.text_share_with, name, age)
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, getString(R.string.label_share_with)))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}