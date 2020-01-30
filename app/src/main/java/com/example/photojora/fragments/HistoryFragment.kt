package com.example.photojora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photojora.R
import com.example.photojora.adapters.RecipesRecyclerViewAdapter
import com.example.photojora.models.Recipe
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.fragment_history, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initApp()
    }

    private fun initApp() {
        initToolbar()
        uploadHistoryList()
    }

    private fun initToolbar() {
        val toolbar = toolbar_custom
        toolbar.title = getString(R.string.toolbar_title_history)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
    }

    private fun uploadHistoryList() {
        val recyclerView = recycler_view_history_recipes
        val adapter = RecipesRecyclerViewAdapter(context!!)

        // init the list
        adapter.addRecipe(Recipe("Блинчики", 20,
            ContextCompat.getDrawable(context!!, R.drawable.ic_pancake)!!
        ))
        adapter.addRecipe(Recipe("Салат", 10,
            ContextCompat.getDrawable(context!!, R.drawable.ic_salad)!!
        ))
        adapter.addRecipe(Recipe("Блинчики", 20,
            ContextCompat.getDrawable(context!!, R.drawable.ic_pancake)!!
        ))
        adapter.addRecipe(Recipe("Салат", 10,
            ContextCompat.getDrawable(context!!, R.drawable.ic_salad)!!
        ))

        // set up RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}