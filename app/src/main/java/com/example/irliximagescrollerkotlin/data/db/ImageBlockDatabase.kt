package com.example.irliximagescrollerkotlin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.irliximagescrollerkotlin.data.ImageBlock

@Database(entities = [ImageBlock::class], version = 1)
abstract class ImageBlockDatabase : RoomDatabase() {

    abstract fun imageBlockDao(): ImageBlockDao
}