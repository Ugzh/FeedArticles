package com.example.feedarticles.mainRecyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R

class ItemsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_rv_title)
    val tvCategory = itemView.findViewById<TextView>(R.id.tv_rv_category)
    val tvDescription = itemView.findViewById<TextView>(R.id.tv_rv_description)
    val ivPicture = itemView.findViewById<ImageView>(R.id.img_rv_picture)
    val clLayout = itemView.findViewById<ConstraintLayout>(R.id.cl_rv)
}