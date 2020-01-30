package com.example.photojora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photojora.R
import com.example.photojora.models.RecipeDescription
import kotlinx.android.synthetic.main.item_recipe_description.view.*

class RecipesDescriptionRecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecipesDescriptionRecyclerViewAdapter.RecipesDescriptionViewHolder>() {

    private val mList = mutableListOf<RecipeDescription>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipesDescriptionViewHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.item_recipe_description, parent, false)
        return RecipesDescriptionViewHolder(v)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecipesDescriptionViewHolder, position: Int) {
        holder.bindViews(mList[position])
    }

    fun addRecipeDescription(recipeDescription: RecipeDescription) = mList.add(recipeDescription)

    inner class RecipesDescriptionViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bindViews(recipeDescription: RecipeDescription) {
            val description = view.text_view_recipe_description_step
            val image = view.image_view_recipe_description

            description.text = recipeDescription.description
            image.setImageDrawable(recipeDescription.image)
        }
    }
}