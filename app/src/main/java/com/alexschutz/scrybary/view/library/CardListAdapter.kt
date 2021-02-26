package com.alexschutz.scrybary.view.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.ItemCardBinding
import com.alexschutz.scrybary.model.Card
import androidx.navigation.Navigation
import com.alexschutz.scrybary.hideKeyboard

class CardListAdapter(private val cardList: ArrayList<Card>) :
    RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    class CardViewHolder(var view: ItemCardBinding, var cards: ArrayList<Card>) : RecyclerView.ViewHolder(view.root), CardClickListener {

        init {
            view.listener = this
        }

        override fun onCardClicked(v: View) {

            v.hideKeyboard()

            val action = LibraryFragmentDirections.actionLibraryFragmentToDetailFragment(cards[adapterPosition])
            Navigation.findNavController(v).navigate(action)
        }
    }

    fun updateCardList(newCardList: List<Card>) {

        cardList.clear()
        cardList.addAll(newCardList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCardBinding>(inflater, R.layout.item_card, parent, false)

        return CardViewHolder(view, cardList)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        with (holder.view) {
           card = cardList[position]
            if (card?.power != null ||card?.toughness != null)
                tvSlash.visibility = View.VISIBLE
            else tvSlash.visibility = View.GONE
        }
    }

    override fun getItemCount() = cardList.size


}