package com.example.irliximagescrollerkotlin.ui.scroller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class FetchingMethod {
    Coroutine, RetrofitEnqueue, RetrofitExecute
}

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var imageBlocks: List<ImageBlock>

    fun getImageBlocks(obAdapterListener: AdapterListener,
                       fetchingMethod: FetchingMethod,
                       filterString: String? = "") {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                if (fetchingMethod == FetchingMethod.Coroutine) {
                    imageBlocks = repository.getImageBlocksByCoroutine()
                    filterAndPassToAdapter(obAdapterListener, filterString)
                } else if (fetchingMethod == FetchingMethod.RetrofitEnqueue ||
                            fetchingMethod == FetchingMethod.RetrofitExecute) {
                    repository.getImageBlocksByCall(fetchingMethod) { fetchedImageBlocks, isDatabaseFilled ->
                        imageBlocks = fetchedImageBlocks
                        if (isDatabaseFilled == false) {
                            viewModelScope.launch(Dispatchers.IO) {
                                repository.passImageBlocksToDatabase(imageBlocks)
                            }
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            filterAndPassToAdapter(obAdapterListener, filterString)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Main", "Error : ${e.message}")
            }
        }
    }

    private suspend fun filterAndPassToAdapter(obAdapterListener: AdapterListener,
                                       filterString: String? = "") {
        if (filterString != "") {
            imageBlocks = filter(filterString?.lowercase())
        }

        withContext(Dispatchers.Main) {
            obAdapterListener.passToAdapter(imageBlocks)
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