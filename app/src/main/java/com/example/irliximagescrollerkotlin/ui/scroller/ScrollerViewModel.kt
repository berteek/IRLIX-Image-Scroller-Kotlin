package com.example.irliximagescrollerkotlin.ui.scroller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var imageBlocks: List<ImageBlock>

    fun getImageBlocks(obAdapterListener: AdapterListener, filterString: String? = "") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                imageBlocks = repository.getImageBlocks()

                if (filterString != "") {
                    imageBlocks = filter(filterString?.lowercase())
                }

                withContext(Dispatchers.Main) {
                    obAdapterListener.passToAdapter(imageBlocks)
                }

            } catch (e: Exception) {
                Log.e("Main", "Error : ${e.message}")
            }
        }
    }

    private fun filter(filterString: String?): MutableList<ImageBlock> {
        val filteredImageBlocks: MutableList<ImageBlock> = mutableListOf()

        for (imageBlock in imageBlocks) {
            val tagsList = imageBlock.tags.split(",")
            loop@ for (tag in tagsList) {
                if (tag.contains(filterString.toString())) {
                    filteredImageBlocks.add(imageBlock)
                    break@loop
                }
            }
        }

        return filteredImageBlocks
    }
}