package com.example.irliximagescrollerkotlin.api

import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.google.gson.annotations.SerializedName

data class PixabayResponse(

    @SerializedName("hits")
    val imageBlocks: List<ImageBlock>
)
