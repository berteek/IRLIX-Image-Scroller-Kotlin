package com.example.irliximagescrollerkotlin.ui.scroller

import androidx.lifecycle.ViewModel
import com.example.irliximagescrollerkotlin.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollerViewModel @Inject constructor(private val repository: Repository): ViewModel() {
}