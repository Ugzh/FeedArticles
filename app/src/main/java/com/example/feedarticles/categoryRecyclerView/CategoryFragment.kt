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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LIST_CATEGORY = "ARG_LIST_CATEGORY"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var listCategory: ArrayList<Category>
    private lateinit var fragment: RecyclerFragment
    private var categoryNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listCategory = it.getParcelableArrayList(ARG_LIST_CATEGORY)!!
            //categoryAdapter = it.getParcelable(ARG_ADAPTER_CATEGORY)!!
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

                fragment.refresh(view, categoryNum)
            }
        }
        return view
    }

    fun getCategoryNum() = categoryNum

    fun setFragment(fragment: RecyclerFragment){
        this.fragment = fragment
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