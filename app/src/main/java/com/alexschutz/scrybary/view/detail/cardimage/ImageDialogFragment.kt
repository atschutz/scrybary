package com.alexschutz.scrybary.view.detail.cardimage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alexschutz.scrybary.databinding.FragmentImageDialogBinding
import com.alexschutz.scrybary.view.BackButtonListener
import com.alexschutz.scrybary.viewmodel.CardImageViewModel


class ImageDialogFragment(val printingsUri: String?, val isFront: Boolean): AppCompatDialogFragment(), BackButtonListener {

    private lateinit var binding: FragmentImageDialogBinding
    private lateinit var viewModel: CardImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentImageDialogBinding.inflate(inflater, container, false)

        binding.backListener = this
        binding.vpImages.adapter = CardImageStateAdapter(this.childFragmentManager, this.lifecycle)

        // Only way I could find to get the background to be transparent. -_-
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CardImageViewModel::class.java)
        viewModel.printingsUri.value = printingsUri

        viewModel.fetchUris()

        if (isFront) {
            viewModel.frontSets.observe(viewLifecycleOwner, { cardSets ->
                val adapter = CardImageStateAdapter(childFragmentManager, lifecycle)
                for (cardSet in cardSets) adapter.fragments.add(CardImageFragment(cardSet))

                adapter.notifyDataSetChanged()

                binding.vpImages.adapter = adapter
            })
        } else {
            viewModel.backSets.observe(viewLifecycleOwner, { cardSets ->
                val adapter = CardImageStateAdapter(childFragmentManager, lifecycle)
                for (cardSet in cardSets) adapter.fragments.add(CardImageFragment(cardSet))

                adapter.notifyDataSetChanged()

                binding.vpImages.adapter = adapter
            })
        }
    }

    override fun onBackPressed(v: View) {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}