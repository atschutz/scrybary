package com.alexschutz.scrybary.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.ItemLegalityBinding
import com.alexschutz.scrybary.model.Legality

class LegalityListAdapter(private val legalityList: ArrayList<Legality>) : RecyclerView.Adapter<LegalityListAdapter.LegalityViewHolder>() {

    class LegalityViewHolder(var view: ItemLegalityBinding) : RecyclerView.ViewHolder(view.root)

    fun updateLegalityList(newLegalityList: List<Legality>) {

        legalityList.clear()
        legalityList.addAll(newLegalityList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegalityViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemLegalityBinding>(inflater, R.layout.item_legality, parent, false)

        return LegalityViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegalityViewHolder, position: Int) {

        holder.view.legality = legalityList[position]
    }

    override fun getItemCount() = legalityList.size
}