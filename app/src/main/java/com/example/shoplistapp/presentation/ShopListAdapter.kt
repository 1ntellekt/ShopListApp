package com.example.shoplistapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplistapp.R
import com.example.shoplistapp.databinding.ItemShopDisabledBinding
import com.example.shoplistapp.databinding.ItemShopEnabledBinding
import com.example.shoplistapp.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : ListAdapter<ShopItem,ShopItemViewHolder>(ShopItemDiffCallback()) {

/*    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(shopList,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }*/

    var onShopItemLongClickListener:((ShopItem)->Unit)? = null
    var onShopItemClickListener:((ShopItem)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("tagAdapter", "onCreateViewHolder()")
        val layoutId = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return ShopItemViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.binding.apply {
            root.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            root.setOnClickListener{
                onShopItemClickListener?.invoke(shopItem)
            }

/*            if (shopItem.enable){
                tvName.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }*/
        }
        when(holder.binding){
            is ItemShopEnabledBinding -> {
               holder.binding.shopItem = shopItem
            }
            is ItemShopDisabledBinding -> {
                holder.binding.shopItem = shopItem
            }
        }
    }

    // set default values when viewHolder recycled
/*    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            tvCount.text = ""
            tvName.text = ""
            tvName.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
            tvCount.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
        }
    }*/

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 15
    }

}