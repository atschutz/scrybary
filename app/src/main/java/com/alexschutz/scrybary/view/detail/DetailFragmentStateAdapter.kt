package com.alexschutz.scrybary.view.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexschutz.scrybary.model.Card

class DetailFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    var card: Card? = null
    var fragments = listOf(
        ImageFragment(),
        InfoFragment(),
        RulingsFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    public override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}