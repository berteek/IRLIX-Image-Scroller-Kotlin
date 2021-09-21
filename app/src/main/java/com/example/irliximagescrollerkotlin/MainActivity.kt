package com.example.irliximagescrollerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.irliximagescrollerkotlin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAndInflate()
    }

    private fun bindAndInflate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}