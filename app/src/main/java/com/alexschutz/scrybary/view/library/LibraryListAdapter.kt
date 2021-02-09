package com.alexschutz.scrybary.view.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.ItemCardBinding
import com.alexschutz.scrybary.model.Card

class LibraryListAdapter(val cardList: ArrayList<Card>) : RecyclerView.Adapter<LibraryListAdapter.CardViewHolder>() {

    class CardViewHolder(var view: ItemCardBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCardBinding>(inflater, R.layout.item_card, parent,  false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.view.card = cardList[position]
    }

    override fun getItemCount(): Int = cardList.size
}