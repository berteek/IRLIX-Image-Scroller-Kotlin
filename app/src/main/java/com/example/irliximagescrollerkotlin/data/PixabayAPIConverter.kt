package com.example.irliximagescrollerkotlin.data

import com.example.irliximagescrollerkotlin.api.PixabayAPI
import com.example.irliximagescrollerkotlin.api.PixabayResponse
import kotlinx.coroutines.GlobalScope
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayAPIConverter @Inject constructor(private val pixabayAPI: PixabayAPI){

}