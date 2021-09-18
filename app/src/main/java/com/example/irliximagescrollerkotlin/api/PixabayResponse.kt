package com.example.irliximagescrollerkotlin.api

import com.example.irliximagescrollerkotlin.data.ImageBlock

data class PixabayResponse(
    val hits: List<ImageBlock>
)
