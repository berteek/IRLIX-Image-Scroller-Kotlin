package com.example.irliximagescrollerkotlin.data

import android.util.Log
import com.example.irliximagescrollerkotlin.api.PixabayAPI
import com.example.irliximagescrollerkotlin.api.PixabayResponse
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDao
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val pixabayAPI: PixabayAPI,
    private val database: ImageBlockDatabase
) {

    private val dao: ImageBlockDao = database.imageBlockDao()

    suspend fun getImageBlocksByCoroutine(): List<ImageBlock> {
        if (dao.getAll().isNullOrEmpty()) {
            val imageBlocks = pixabayAPI.searchImages().imageBlocks
            dao.insertAll(imageBlocks)
        }
        return dao.getAll()
    }

    suspend fun getImageBlocksByCall(onImageBlocksFetched: (List<ImageBlock>, Boolean) -> Unit) {
        if (dao.getAll().isNullOrEmpty()) {
            retrofitEnqueue {
                onImageBlocksFetched(it, false)
            }
        } else {
            onImageBlocksFetched(dao.getAll(), true)
        }
    }

    suspend fun passImageBlocksToDatabase(imageBlocks: List<ImageBlock>) {
        dao.insertAll(imageBlocks)
    }

    private fun retrofitEnqueue(onImageBlocksFetched: (List<ImageBlock>) -> Unit) {
        val call = pixabayAPI.searchImagesByCall()
        val TAG = "RetrofitEnqueue"
        var imageBlocks: List<ImageBlock>

        call.enqueue(object : Callback<PixabayResponse> {
            override fun onResponse(
                call: Call<PixabayResponse>,
                response: Response<PixabayResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Response was not successful " + response.code())
                    return
                }

                if (response.body()?.imageBlocks == null) {
                    Log.e(TAG, "Response is null")
                    return
                } else {
                    imageBlocks = response.body()!!.imageBlocks
                    onImageBlocksFetched(imageBlocks)
                }
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data")
            }
        })
    }

    private suspend fun retrofitExecute(): List<ImageBlock> {
        return emptyList()
    }
}