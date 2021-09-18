package com.example.irliximagescrollerkotlin.ui.scroller

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.irliximagescrollerkotlin.R
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.databinding.FragmentScrollerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrollerFragment : Fragment(R.layout.fragment_scroller) {

    private val viewModel by viewModels<ScrollerViewModel>()

    private var _binding: FragmentScrollerBinding? = null
    private val binding get() = _binding!!

    private var imageBlocks: List<ImageBlock>? = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentScrollerBinding.bind(view)

        val adapter = ScrollerAdapter()

        binding.apply {
            rcView.setHasFixedSize(true)
            rcView.adapter = adapter
        }

        imageBlocks = viewModel.getImageBlocks(viewLifecycleOwner)

        adapter.setImageBlocks(imageBlocks)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}