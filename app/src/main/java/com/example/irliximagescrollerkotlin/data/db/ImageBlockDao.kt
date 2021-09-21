package com.example.irliximagescrollerkotlin.data.db

import androidx.room.*
import com.example.irliximagescrollerkotlin.data.ImageBlock

@Dao
interface ImageBlockDao {

    @Query("SELECT * FROM image_block_table")
    suspend fun getAll(): List<ImageBlock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageBlock: ImageBlock)

    @Update
    suspend fun update(imageBlock: ImageBlock)

    @Delete
    suspend fun delete(imageBlock: ImageBlock)

}