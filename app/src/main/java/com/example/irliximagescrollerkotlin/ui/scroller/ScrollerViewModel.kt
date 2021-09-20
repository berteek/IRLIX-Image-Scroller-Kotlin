package com.example.irliximagescrollerkotlin.ui.scroller

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

//    init {
//        Log.e("VIEWMODEL", "viewModel loaded")
//    }

    suspend fun getImageBlocks(): List<ImageBlock>? = repository.getImageBlocks()

    fun test() { Log.e("MESSAGE", "MESSAGE")}
}