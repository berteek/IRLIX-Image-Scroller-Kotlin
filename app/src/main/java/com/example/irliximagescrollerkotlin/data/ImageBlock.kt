package com.example.irliximagescrollerkotlin.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageBlock(
    val id: Int,
    @SerializedName("webformatURL") val imageURL: String,
    val tags: String,
    val likes: Int,
    val userImageURL: String,
    @SerializedName("user") val username: String
): Parcelable {

}
