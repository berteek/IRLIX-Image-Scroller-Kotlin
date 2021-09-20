package com.example.irliximagescrollerkotlin.ui.scroller

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.irliximagescrollerkotlin.R
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.data.Repository
import com.example.irliximagescrollerkotlin.databinding.FragmentScrollerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ScrollerFragment : Fragment(R.layout.fragment_scroller) {

    private val viewModel by viewModels<ScrollerViewModel>()

    private var _binding: FragmentScrollerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ScrollerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScrollerBinding.bind(view)

        setAdapter()
        viewModel.somehowInitiateViewModel()
        passImageBlocksToAdapter()
    }

    private fun passImageBlocksToAdapter() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                var imageBlocks: List<ImageBlock>? = viewModel.getImageBlocks()

                withContext(Dispatchers.Main) {
                    adapter.setImageBlocks(imageBlocks)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error : ${e.message}")
            }
        }
    }

    private fun setAdapter() {
        adapter = ScrollerAdapter()

        binding.apply {
            rcView.setHasFixedSize(true)
            rcView.layoutManager = LinearLayoutManager(activity)
            rcView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}