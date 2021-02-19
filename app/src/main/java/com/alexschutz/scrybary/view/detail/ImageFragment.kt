package com.alexschutz.scrybary.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.alexschutz.scrybary.databinding.FragmentImageBinding
import com.alexschutz.scrybary.viewmodel.DetailViewModel

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(parentFragment as ViewModelStoreOwner).get(DetailViewModel::class.java)

        viewModel.cardImageUri.observe(viewLifecycleOwner, { uri ->
            binding.imageUri = uri
        })
    }
}