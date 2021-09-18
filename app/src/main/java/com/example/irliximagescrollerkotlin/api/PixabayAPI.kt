package com.example.irliximagescrollerkotlin.api

import retrofit2.Response
import retrofit2.http.GET

interface PixabayAPI {

    companion object {
        const val BASE_URL = "https://pixabay.com/"
    }

    @GET("/api/?key=22771156-513389d498d9d466dffbb652f&q=cat&image_type=photo&pretty=true")
    suspend fun searchImages(): Response<PixabayResponse>
}