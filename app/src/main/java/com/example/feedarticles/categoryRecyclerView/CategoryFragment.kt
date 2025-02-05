package com.example.feedarticles.categoryRecyclerView

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.feedarticles.R
import com.example.feedarticles.mainRecyclerView.RecyclerFragment

private const val ARG_LIST_CATEGORY = "ARG_LIST_CATEGORY"


class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var listCategory: ArrayList<Category>
    private var recyclerFragment: RecyclerFragment? = null
    private var categoryNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listCategory = it.getParcelableArrayList(ARG_LIST_CATEGORY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rv_category_items)
        val categoryAdapter = CategoryAdapter().apply { setCategories(listCategory) }

        rv.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        categoryAdapter.apply {
            setSendCategoryCallback {
                categoryNum = when(it){
                    "Sport" -> 1
                    "Manga" -> 2
                    "Divers" -> 3
                    else -> 0
                }
                recyclerFragment?.refresh(view, categoryNum)
            }
        }
        return view
    }

    fun getCategoryNum() = categoryNum

    fun setFragment(recyclerFragment: RecyclerFragment){
        this.recyclerFragment = recyclerFragment
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryList : ArrayList<Category>, ) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_LIST_CATEGORY,categoryList)
                }
            }
    }
}