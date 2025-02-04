package com.example.feedarticles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticles.dtos.ItemDto
import com.example.feedarticles.mainRecyclerView.ItemsAdapter

class RecyclerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false)
        val view : View = inflater.inflate(R.layout.fragment_main, container, false)

        val itemAdapter: ItemsAdapter = ItemsAdapter().apply {
            setItems(arrayListOf(
                ItemDto("test", "x", 1, "x", 0, "x"),
                ItemDto("test2", "x", 2, "x", 0, "x")
            ))
        }

        val rv = view.findViewById<RecyclerView>(R.id.rv_items)
        rv.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = itemAdapter
        }
        return view
    }
}