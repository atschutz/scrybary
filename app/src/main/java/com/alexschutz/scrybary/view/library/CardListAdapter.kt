package com.alexschutz.scrybary.view.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.ItemCardBinding
import com.alexschutz.scrybary.model.Card

class CardListAdapter(private val cardList: ArrayList<Card>) :
    RecyclerView.Adapter<CardListAdapter.CardViewHolder>(), CardClickListener {

    class CardViewHolder(var view: ItemCardBinding) : RecyclerView.ViewHolder(view.root)

    fun updateCardList(newCardList: List<Card>) {

        cardList.clear()
        cardList.addAll(newCardList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCardBinding>(inflater, R.layout.item_card, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.view.card = cardList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int = cardList.size

    override fun onCardClicked(v: View) {
        Toast.makeText(
            v.context,
            "You clicked $v",
            Toast.LENGTH_LONG
        ).show()
    }
}