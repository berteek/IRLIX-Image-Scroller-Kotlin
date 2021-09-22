package com.example.irliximagescrollerkotlin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "image_block_table")
@Parcelize
data class ImageBlock(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("webformatURL") val imageURL: String,
    @SerializedName("largeImageURL") val highResolutionImageURL: String,
    val tags: String,
    val likes: Long,
    val views: Long,
    val downloads: Long,
    val userImageURL: String,
    @SerializedName("user") val username: String
): Parcelable {

}
