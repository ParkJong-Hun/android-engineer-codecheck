/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codecheck.databinding.FragmentOneBinding

class OneFragment: Fragment(R.layout.fragment_one){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOneBinding.bind(view)

        val viewModel= OneViewModel(context!!)

        val layoutManager= LinearLayoutManager(context!!)
        val dividerItemDecoration=
            DividerItemDecoration(context!!, layoutManager.orientation)
        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(searchResult: SearchResult){
                gotoRepositoryFragment(searchResult)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        viewModel.searchResults(it).apply{
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also{
            it.layoutManager= layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter= adapter
        }
    }

    fun gotoRepositoryFragment(searchResult: SearchResult)
    {
        val action= OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(searchResult)
        findNavController().navigate(action)
    }
}

val diff_util= object: DiffUtil.ItemCallback<SearchResult>(){
    override fun areItemsTheSame(oldSearchResult: SearchResult, newSearchResult: SearchResult): Boolean
    {
        return oldSearchResult.name== newSearchResult.name
    }

    override fun areContentsTheSame(oldSearchResult: SearchResult, newSearchResult: SearchResult): Boolean
    {
        return oldSearchResult== newSearchResult
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<SearchResult, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
    	fun itemClick(searchResult: SearchResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
    	val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
    	return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
    	val item= getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

    	holder.itemView.setOnClickListener{
     		itemClickListener.itemClick(item)
    	}
    }
}
