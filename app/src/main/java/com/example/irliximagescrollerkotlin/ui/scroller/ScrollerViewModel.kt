package com.example.irliximagescrollerkotlin.ui.scroller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun getImageBlocks(viewLifecycleOwner: LifecycleOwner): List<ImageBlock>? {
        return repository.getImageBlocks(viewLifecycleOwner)
    }
}