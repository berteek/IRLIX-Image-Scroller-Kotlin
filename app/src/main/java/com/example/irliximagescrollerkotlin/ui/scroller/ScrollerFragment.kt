package com.example.irliximagescrollerkotlin.ui.scroller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
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

    private lateinit var imageBlocks: List<ImageBlock>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScrollerBinding.bind(view)

        setAdapter()
        viewModel
        getAndDisplayImageBlocks()
        setSearchFilter()
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
                getAndDisplayImageBlocks(newText)

                return false
            }
        })
    }

    private fun getAndDisplayImageBlocks(filterString: String? = "") {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                imageBlocks = viewModel.getImageBlocks()

                if (filterString != "") {
                    imageBlocks = filter(filterString)
                }

                withContext(Dispatchers.Main) {
                    adapter.setImageBlocks(imageBlocks)
                }

            } catch (e: Exception) {
                Log.e("Main", "Error : ${e.message}")
            }
        }
    }

    private fun filter(filterString: String?): MutableList<ImageBlock> {
        val filteredImageBlocks: MutableList<ImageBlock> = mutableListOf()

        for (imageBlock in imageBlocks) {
            val tagsList = imageBlock.tags.split(",")
            loop@ for (tag in tagsList) {
                if (tag.contains(filterString.toString())) {
                    filteredImageBlocks.add(imageBlock)
                    break@loop
                }
            }
        }

        return filteredImageBlocks
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