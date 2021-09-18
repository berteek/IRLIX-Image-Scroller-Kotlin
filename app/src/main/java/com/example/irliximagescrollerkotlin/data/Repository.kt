package com.example.irliximagescrollerkotlin.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.irliximagescrollerkotlin.api.PixabayAPI
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val pixabayAPI: PixabayAPI) {

    private var imageBlocks: List<ImageBlock>? = emptyList()

    fun getImageBlocks(viewLifecycleOwner: LifecycleOwner): List<ImageBlock>? {
        viewLifecycleOwner.lifecycleScope.launch {
            imageBlocks = pixabayAPI.searchImages().body()?.imageBlocks
        }

        return imageBlocks
    }
}