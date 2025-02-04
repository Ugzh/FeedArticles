package com.example.feedarticles.categoryRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class CategoryAdapter: RecyclerView.Adapter<CategoryHolder>() {
    private var categoriesList = mutableListOf<Category>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.category_rv_categories, parent, false)
            .let {
                return CategoryHolder(it)
            }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        with(holder){
            categoriesList.get(position).let {
                tvCategory.text = it.name
            }
        }
    }

    fun setCategories(categoryList : List<Category>){
        with(categoriesList){
            clear()
            addAll(categoryList)
        }
    }
}