package com.example.feedarticles.categoryRecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class CategoryAdapter: RecyclerView.Adapter<CategoryHolder>() {
    private var categoriesList = mutableListOf<Category>()
    private var sendCategoryCallback: ((String) -> Unit)? = null
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
                clRv.setOnClickListener{ _->
                    sendCategoryCallback?.invoke(it.name)
                   /* when(it.name){
                        "Sport" -> sendCategoryCallback?.invoke(1)
                        "Manga" -> sendCategoryCallback?.invoke(2)
                        "Divers" -> sendCategoryCallback?.invoke(3)
                        else -> return@setOnClickListener
                    }*/
                }
            }
        }
    }

    fun setCategories(categoryList : List<Category>){
        with(categoriesList){
            clear()
            addAll(categoryList)
        }
    }

    fun setSendCategoryCallback(sendCategoryCallback : (String) -> Unit){
        this.sendCategoryCallback = sendCategoryCallback
    }
}