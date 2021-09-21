package com.example.irliximagescrollerkotlin.ui.scroller

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun getImageBlocks(): List<ImageBlock> = repository.getImageBlocks()
}