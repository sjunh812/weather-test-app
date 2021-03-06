package org.sjhstudio.weathertestapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.weathertestapp.R
import org.sjhstudio.weathertestapp.databinding.ItemSearchBinding
import org.sjhstudio.weathertestapp.model.Addresses

interface SearchAdapterCallback {
    fun onSelected(item: Addresses)
}

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items = arrayListOf<Addresses>()
    private var callback: SearchAdapterCallback? = null

    fun setOnSearchAdapterCallback(callback: SearchAdapterCallback) {
        this.callback = callback
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val binding = ItemSearchBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                val item = items[adapterPosition]
                callback?.onSelected(item)
            }
        }

        fun setBind(data: Addresses) {
            val roadAddress = data.roadAddress
            binding.searchItemTv.text = roadAddress
        }

    }

    fun setItems(items: ArrayList<Addresses>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.setBind(items[position])
    }

    override fun getItemCount(): Int = items.size

}