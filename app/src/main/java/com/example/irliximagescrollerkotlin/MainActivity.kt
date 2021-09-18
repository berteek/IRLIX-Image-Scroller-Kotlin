package com.example.irliximagescrollerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.irliximagescrollerkotlin.databinding.ActivityMainBinding
import com.example.irliximagescrollerkotlin.ui.scroller.RecyclerViewFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAndInflate()
    }

    private fun bindAndInflate(): Unit {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}