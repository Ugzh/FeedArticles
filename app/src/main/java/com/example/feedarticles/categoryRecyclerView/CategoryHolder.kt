package com.example.feedarticles.categoryRecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCategory = itemView.findViewById<TextView>(R.id.tv_categoryRv_category)
}