package com.example.photojora.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photojora.R
import com.example.photojora.activities.RecipeActivity
import com.example.photojora.models.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipesRecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipesViewHolder>() {

    private val mList = mutableListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return RecipesViewHolder(v)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bindViews(mList[position])
    }

    fun addRecipe(recipe: Recipe) = mList.add(recipe)

    inner class RecipesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindViews(recipe: Recipe) {
            // bind views
            val name = view.text_view_item_recipe_name
            val time = view.text_view_item_recipe_time
            val image = view.image_view_item_recipe

            name.text = recipe.name
            time.text = "${recipe.time} мин"
            image.setImageDrawable(recipe.image)

            // open RecipeActivity
            view.item_recipe_layout.setOnClickListener {
                val intent = Intent(context, RecipeActivity::class.java)
                intent.putExtra("toolbar_title", recipe.name)

                context.startActivity(intent)
            }
        }
    }
}