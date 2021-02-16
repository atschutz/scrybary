package com.alexschutz.scrybary.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.ItemRulingBinding
import com.alexschutz.scrybary.model.Ruling

class RulingsListAdapter(private val rulingsList: ArrayList<Ruling>): RecyclerView.Adapter<RulingsListAdapter.RulingViewHolder>() {

    class RulingViewHolder(var view: ItemRulingBinding) : RecyclerView.ViewHolder(view.root)

    fun updateRulingsList(newRulingsList: List<Ruling>) {

        rulingsList.clear()
        rulingsList.addAll(newRulingsList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RulingViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemRulingBinding>(inflater, R.layout.item_ruling, parent, false)

        return RulingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RulingViewHolder, position: Int) {

        holder.view.ruling = rulingsList[position]
    }

    override fun getItemCount() = rulingsList.size
}