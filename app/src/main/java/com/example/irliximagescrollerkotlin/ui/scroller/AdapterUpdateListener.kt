package com.example.irliximagescrollerkotlin.ui.scroller

import com.example.irliximagescrollerkotlin.data.ImageBlock

class AdapterListener {

    var passToAdapter: (List<ImageBlock>) -> Unit = {}
}