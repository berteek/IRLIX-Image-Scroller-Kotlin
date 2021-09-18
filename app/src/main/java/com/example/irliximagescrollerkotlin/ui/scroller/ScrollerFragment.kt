package com.example.irliximagescrollerkotlin.ui.scroller

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.irliximagescrollerkotlin.R
import com.example.irliximagescrollerkotlin.data.ImageBlock
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrollerFragment : Fragment(R.layout.fragment_scroller) {

    private val viewModel by viewModels<ScrollerViewModel>()
    private var imageBlocks: List<ImageBlock>? = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageBlocks = viewModel.getImageBlocks(viewLifecycleOwner)
    }
}