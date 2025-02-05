package com.example.feedarticles.categoryRecyclerView

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class CategoryAdapter() : RecyclerView.Adapter<CategoryHolder>() {
    private var categoriesList = mutableListOf<Category>()
    private var sendCategoryCallback: ((String) -> Unit)? = null
    private var selectedPos: Int? = null
    private val drawableList = listOf(
        R.drawable.category_sport_style,
        R.drawable.category_manga_style,
        R.drawable.category_various_style,
        R.drawable.category_all_style
    )
    private val defaultDrawable = R.drawable.edit_text_style
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
            categoriesList.get(position).let {category ->
                tvCategory.text = category.name
                clRv.setOnClickListener{
                    if (selectedPos == position)
                        sendCategoryCallback?.invoke("")
                    else
                        sendCategoryCallback?.invoke(category.name)

                    val prevPos = selectedPos
                    selectedPos = if (selectedPos == position) null else position

                    prevPos?.let { notifyItemChanged(it) }
                    notifyItemChanged(position)
                }

                clRv.setBackgroundResource(
                    if(selectedPos == position)
                        drawableList.get(position)
                    else defaultDrawable
                )
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