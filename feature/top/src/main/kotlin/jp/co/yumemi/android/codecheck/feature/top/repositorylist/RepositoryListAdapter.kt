package jp.co.yumemi.android.codecheck.feature.top.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.databinding.LayoutSearchedRepositoryBinding

class RepositoryListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<SearchedRepository, RepositoryListAdapter.ViewHolder>(
    RepositoryDiffCallback()
) {
    class ViewHolder(
        private val binding: LayoutSearchedRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchedRepository, clickListener: OnItemClickListener) {
            binding.repositoryNameView.text = item.name
            binding.root.setOnClickListener {
                clickListener.itemClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSearchedRepositoryBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding)
            }
        }
    }

    fun interface OnItemClickListener {
        fun itemClick(searchedRepositoryItemInfo: SearchedRepository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    private class RepositoryDiffCallback : DiffUtil.ItemCallback<SearchedRepository>() {
        override fun areItemsTheSame(
            oldItem: SearchedRepository,
            newItem: SearchedRepository
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: SearchedRepository,
            newItem: SearchedRepository
        ): Boolean {
            return oldItem == newItem
        }
    }
}
