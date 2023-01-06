package com.example.ageprediction.ui.favorites.childrenFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ageprediction.R

class CustomDialogDelete: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.delete_dialog_round_corners)
        return inflater.inflate(R.layout.custom_dialog_delete, container, false)
    }

}