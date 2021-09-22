package com.example.irliximagescrollerkotlin.ui.scroller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
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
class ScrollerFragment : Fragment(R.layout.fragment_scroller), ScrollerAdapter.OnImageBlockClickListener {

    private val viewModel by viewModels<ScrollerViewModel>()

    private var _binding: FragmentScrollerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ScrollerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScrollerBinding.bind(view)

        setAdapter()
        viewModel
        passImageBlocksToAdapter()
        setSearchFilter()
    }

    private fun passImageBlocksToAdapter() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                var imageBlocks: List<ImageBlock> = viewModel.getImageBlocks()

                withContext(Dispatchers.Main) {
                    adapter.setImageBlocks(imageBlocks)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error : ${e.message}")
            }
        }
    }

    private fun setAdapter() {
        adapter = ScrollerAdapter(this)

        binding.apply {
            rcView.setHasFixedSize(true)
            rcView.layoutManager = LinearLayoutManager(activity)
            rcView.adapter = adapter
        }
    }

    private fun setSearchFilter() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val imageBlocks: List<ImageBlock> = viewModel.getImageBlocks()

                        var filteredImageBlocks: MutableList<ImageBlock> = mutableListOf()

                        for (imageBlock in imageBlocks) {
                            var tagsList = imageBlock.tags.split(",")
                            loop@ for (tag in tagsList) {
                                if (tag.contains(newText.toString())) {
                                    filteredImageBlocks.add(imageBlock)
                                    break@loop
                                }
                            }
                        }

                        withContext(Dispatchers.Main) {
                            adapter.setImageBlocks(filteredImageBlocks)
                        }
                    } catch (e: Exception) {
                        Log.e("Main", "Error : ${e.message}")
                    }
                }

                return false
            }
        })
    }

    override fun onImageBlockClick(imageBlock: ImageBlock) {
        val action = ScrollerFragmentDirections.actionRecyclerViewFragmentToInformationFragment(imageBlock)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}