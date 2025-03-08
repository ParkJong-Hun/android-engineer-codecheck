package jp.co.yumemi.android.codecheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

private val repository_list_diff_util = object : DiffUtil.ItemCallback<SearchedRepositoryItemInfo>() {
    override fun areItemsTheSame(
        oldSearchedRepositoryItemInfo: SearchedRepositoryItemInfo,
        newSearchedRepositoryItemInfo: SearchedRepositoryItemInfo
    ): Boolean {
        return oldSearchedRepositoryItemInfo.name == newSearchedRepositoryItemInfo.name
    }

    override fun areContentsTheSame(
        oldSearchedRepositoryItemInfo: SearchedRepositoryItemInfo,
        newSearchedRepositoryItemInfo: SearchedRepositoryItemInfo
    ): Boolean {
        return oldSearchedRepositoryItemInfo == newSearchedRepositoryItemInfo
    }
}

class RepositoryListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<SearchedRepositoryItemInfo, RepositoryListAdapter.ViewHolder>(repository_list_diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(searchedRepositoryItemInfo: SearchedRepositoryItemInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_searched_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
