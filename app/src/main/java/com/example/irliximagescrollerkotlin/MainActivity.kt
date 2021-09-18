package com.example.irliximagescrollerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.irliximagescrollerkotlin.databinding.ActivityMainBinding
import com.example.irliximagescrollerkotlin.fragments.RecyclerViewFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, RecyclerViewFragment.newInstance())
            .commit()
    }
}