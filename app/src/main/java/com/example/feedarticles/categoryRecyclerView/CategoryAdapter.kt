package com.example.feedarticles.categoryRecyclerView

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class CategoryAdapter() : RecyclerView.Adapter<CategoryHolder>(), Parcelable {
    private var categoriesList = mutableListOf<Category>()
    private var sendCategoryCallback: ((String) -> Unit)? = null

    constructor(parcel: Parcel) : this() {

    }

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
       var count = 0
        with(holder){
            categoriesList.get(position).let {category ->
                tvCategory.text = category.name
                clRv.setOnClickListener{
                    sendCategoryCallback?.invoke(category.name)
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryAdapter> {
        override fun createFromParcel(parcel: Parcel): CategoryAdapter {
            return CategoryAdapter(parcel)
        }

        override fun newArray(size: Int): Array<CategoryAdapter?> {
            return arrayOfNulls(size)
        }
    }
}