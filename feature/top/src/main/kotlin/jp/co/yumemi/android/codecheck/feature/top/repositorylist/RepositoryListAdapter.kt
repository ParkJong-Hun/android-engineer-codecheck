package jp.co.yumemi.android.codecheck.feature.top.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.feature.top.R
import jp.co.yumemi.android.codecheck.feature.top.databinding.LayoutSearchedRepositoryBinding
import java.text.DecimalFormat

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

            if (item.language.isNotEmpty()) {
                binding.languageIndicator.text = item.language
                binding.languageIndicator.visibility = android.view.View.VISIBLE
            } else {
                binding.languageIndicator.visibility = android.view.View.GONE
            }

            val formattedStarsCount = formatStarCount(item.stargazersCount)
            binding.starsIndicator.text =
                binding.root.context.getString(R.string.stars_count, formattedStarsCount)

            binding.root.setOnClickListener {
                clickListener.itemClick(item)
            }
        }

        private fun formatStarCount(count: Long): String {
            return when {
                count < 1000 -> count.toString()
                count < 10000 -> {
                    val formatted = DecimalFormat("0.0").format(count / 1000.0)
                    "${formatted}k"
                }

                else -> {
                    val formatted = DecimalFormat("0.0").format(count / 1000.0)
                    "${formatted}k"
                }
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
