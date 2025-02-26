package com.example.feedarticles.mainRecyclerView

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.R
import com.example.feedarticles.dtos.ItemDto
import com.squareup.picasso.Picasso

class ItemsAdapter: RecyclerView.Adapter<ItemsHolder>() {
    private val itemsList = mutableListOf<ItemDto>()
    private var itemDetailCallback : ((ItemDto) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_items, parent, false)
            .let {
                return ItemsHolder(it)
            }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        val resource = holder.itemView.resources
        with(holder){
            itemsList.get(position).let {
                tvTitle.text = it.title
                tvCategory.text = when(it.category){
                    1 -> "Sport"
                    2 -> "Manga"
                    3 -> "Divers"
                    else -> return
                }
                tvDescription.text = it.description

                clLayout.background.let { _ ->
                    when(it.category){
                        1 -> clLayout.setBackgroundColor(resource.getColor(R.color.sportColor))
                        2 -> clLayout.setBackgroundColor(resource.getColor(R.color.mangaColor))
                        3 -> clLayout.setBackgroundColor(resource.getColor(R.color.variousColor))
                        else -> return
                    }
                }

                clLayout.setOnClickListener { _ ->
                    itemDetailCallback?.invoke(it)
                }

                Picasso
                    .get()
                    .load(it.urlImage.ifEmpty { "boo" })
                    .placeholder(android.R.drawable.ic_menu_search)
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivPicture)
            }
        }
    }

    fun setItems(newItems : List<ItemDto>){
        with(itemsList){
            clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    fun setItemDetailCallback(itemDetailCallback : (ItemDto) -> Unit ){
        this.itemDetailCallback = itemDetailCallback
    }

}