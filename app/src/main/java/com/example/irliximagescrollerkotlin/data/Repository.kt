package com.example.irliximagescrollerkotlin.data

import com.example.irliximagescrollerkotlin.api.PixabayAPI
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDao
import com.example.irliximagescrollerkotlin.data.db.ImageBlockDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val pixabayAPI: PixabayAPI,
    private val database: ImageBlockDatabase
) {

    private val dao: ImageBlockDao = database.imageBlockDao()

    suspend fun getImageBlocks(): List<ImageBlock> {
        if (dao.getAll().isNullOrEmpty()) {
            var imageBlocks = pixabayAPI.searchImages().imageBlocks

            dao.insertAll(imageBlocks)
        }

        return dao.getAll()
    }
}