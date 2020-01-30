package com.example.photojora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photojora.R
import com.example.photojora.models.Grocery
import kotlinx.android.synthetic.main.item_grocery_list.view.*

class GroceryListRecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<GroceryListRecyclerViewAdapter.GroceryListViewHolder>() {

    private val mList = mutableListOf<Grocery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.item_grocery_list, parent, false)
        return GroceryListViewHolder(v)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: GroceryListViewHolder, position: Int) {
        holder.bindViews(mList[position])
    }

    fun addGrocery(grocery: Grocery) = mList.add(grocery)

    fun clearList() = mList.clear()

    fun getCountOfSelectedItems(): Int {
        var count = 0

        for (item in mList) {
            if (item.checked) count++
        }

        return count
    }

    inner class GroceryListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindViews(grocery: Grocery) {
            val name = view.text_view_item_grocery_name_list
            val checkbox = view.check_box_item_grocery_list

            name.text = grocery.name
            checkbox.isChecked = grocery.checked

            // change state of checkboxes
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                mList[adapterPosition].checked = isChecked
            }
        }
    }
}