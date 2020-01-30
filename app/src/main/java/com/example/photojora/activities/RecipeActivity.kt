package com.example.photojora.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photojora.R
import com.example.photojora.adapters.RecipesDescriptionRecyclerViewAdapter
import com.example.photojora.models.RecipeDescription
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class RecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        initApp()
    }

    private fun initApp() {
        initToolbar()
        initRecipeList()
    }

    private fun initToolbar() {
        val toolbar = toolbar_custom

        toolbar.apply {
            title = intent.getStringExtra("toolbar_title")
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_menu_back)
        }
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initRecipeList() {
        val recyclerView = recycler_view_recipe_description
        val adapter = RecipesDescriptionRecyclerViewAdapter(this)

        // init the list
        adapter.addRecipeDescription(
            RecipeDescription(
                "1. Подготовьте все необходимые ингредиенты.",
                ContextCompat.getDrawable(this, R.drawable.ic_recipe_description)!!
            )
        )
        adapter.addRecipeDescription(
            RecipeDescription(
                "2. В глубокую миску разбейте яйца, добавьте щепотку соли и сахар по вкусу. Слегка взбейте.",
                ContextCompat.getDrawable(this, R.drawable.ic_recipe_description)!!
            )
        )
        adapter.addRecipeDescription(
            RecipeDescription(
                "3. Затем добавьте небольшое количество молока и снова размешайте.",
                ContextCompat.getDrawable(this, R.drawable.ic_recipe_description)!!
            )
        )
        adapter.addRecipeDescription(
            RecipeDescription(
                "4. Всыпьте муку и полностью вмешайте ее в молочно-яичную массу.",
                ContextCompat.getDrawable(this, R.drawable.ic_recipe_description)!!
            )
        )

        // set up RecyclerView
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}