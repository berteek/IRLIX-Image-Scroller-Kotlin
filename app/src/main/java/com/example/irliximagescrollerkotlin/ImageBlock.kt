package com.example.irliximagescrollerkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageBlock(
    val imageURL: String,
    val tags: String,
    val likes: Int,
    val userImageURL: String,
    val username: String
): Parcelable {

}