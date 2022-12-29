package com.example.ageprediction.ui.favorites.childrenFragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ageprediction.databinding.FavoritesListItemBinding

class NamesAdapter(private val showButtonFun: ()-> Unit,
                   private val hideButtonFun: ()-> Unit) :
    ListAdapter<NameItem, NamesAdapter.ItemViewHolder>(FavNamesDiffCallback()) {

    class ItemViewHolder(itemBinding: FavoritesListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        val textName: TextView
        val checkBox: CheckBox

        init {
            textName = itemBinding.textName
            checkBox = itemBinding.checkBox
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = run {
        ItemViewHolder(FavoritesListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    private var longClickProduced = false
    var currentAmountOfCheckedBoxes = 0

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val listItem = getItem(position)

        if (!longClickProduced) {
            holder.itemView.setOnLongClickListener {
                longClickProduced = true
                notifyDataSetChanged() // If user produced long click, we have to update all list to show all checkboxes
                true
            }
        }

        if (longClickProduced) {
            holder.checkBox.visibility = View.VISIBLE
        }

        holder.textName.text = listItem.textName
        holder.checkBox.isChecked = listItem.checkBoxState

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                currentAmountOfCheckedBoxes += 1
                listItem.checkBoxState = true
                showButtonFun()
            } else {
                currentAmountOfCheckedBoxes -= 1
                listItem.checkBoxState = false
                if (currentAmountOfCheckedBoxes == 0) {
                    hideButtonFun()
                }
            }
        }
    }

}

class FavNamesDiffCallback : DiffUtil.ItemCallback<NameItem>() {
    override fun areItemsTheSame(oldItem: NameItem, newItem: NameItem): Boolean {
        return oldItem.textName == newItem.textName
    }

    override fun areContentsTheSame(oldItem: NameItem, newItem: NameItem): Boolean {
        return oldItem == newItem
    }
}