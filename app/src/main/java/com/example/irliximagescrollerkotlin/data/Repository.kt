package com.example.irliximagescrollerkotlin.data

import com.example.irliximagescrollerkotlin.api.PixabayAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val pixabayAPI: PixabayAPI) {
}