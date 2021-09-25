package com.example.irliximagescrollerkotlin.data

import android.os.AsyncTask
import android.util.Log
import com.example.irliximagescrollerkotlin.api.PixabayAPI
import com.example.irliximagescrollerkotlin.api.PixabayResponse
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDao
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDatabase
import com.example.irliximagescrollerkotlin.ui.scroller.FetchingMethod
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
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

    suspend fun getImageBlocksByCall(
        fetchingMethod: FetchingMethod,
        onImageBlocksFetched: (List<ImageBlock>, Boolean) -> Unit
    ) {
        if (dao.getAll().isNullOrEmpty()) {
            if (fetchingMethod == FetchingMethod.RetrofitEnqueue) {
                retrofitEnqueue { onImageBlocksFetched(it, false) }
            } else if (fetchingMethod == FetchingMethod.RetrofitExecute) {
                retrofitExecute { onImageBlocksFetched(it, false) }
            }

        } else {
            onImageBlocksFetched(dao.getAll(), true)
        }
    }

    suspend fun passImageBlocksToDatabase(imageBlocks: List<ImageBlock>) {
        dao.insertAll(imageBlocks)
    }

    private fun retrofitEnqueue(onImageBlocksFetchedEnqueue: (List<ImageBlock>) -> Unit) {
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
                    onImageBlocksFetchedEnqueue(imageBlocks)
                }
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data")
            }
        })
    }

    private lateinit var retrofitExecuteCallback: ((List<ImageBlock>) -> Unit)

    private fun retrofitExecute(onImageBlocksFetched: (List<ImageBlock>) -> Unit) {
        RetrofitExecuteAsyncTask().execute()
        retrofitExecuteCallback = { onImageBlocksFetched(it) }
    }

    private inner class RetrofitExecuteAsyncTask() : AsyncTask<Void, Void, List<ImageBlock>>() {

        private val call = pixabayAPI.searchImagesByCall()
        private val TAG = "RetrofitExecute"
        private lateinit var imageBlocks: List<ImageBlock>

        override fun doInBackground(vararg params: Void?): List<ImageBlock> {
            try {
                val response = call.execute()
                if (!response.isSuccessful) {
                    Log.e(TAG, "Response was not successful " + response.code())
                }

                if (response.body()?.imageBlocks == null) {
                    Log.e(TAG, "Response is null")
                } else {
                    imageBlocks = response.body()!!.imageBlocks
                }
            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
            }

            return imageBlocks
        }

        override fun onPostExecute(result: List<ImageBlock>?) {
            super.onPostExecute(result)

            if (result != null) {
                retrofitExecuteCallback(result)
            } else {
                Log.e(TAG, "Result is null")
            }
        }
    }
}