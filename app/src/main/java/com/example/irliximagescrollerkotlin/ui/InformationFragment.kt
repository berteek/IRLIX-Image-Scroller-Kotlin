package com.example.irliximagescrollerkotlin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.irliximagescrollerkotlin.R
import com.example.irliximagescrollerkotlin.databinding.FragmentInformationBinding

class InformationFragment : Fragment(R.layout.fragment_information) {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<InformationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInformationBinding.bind(view)

        bindViews()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews() {
        binding
        binding.apply {
            Glide.with(this@InformationFragment)
                .load(args.imageBlock.highResolutionImageURL)
                .into(imageView)

            Glide.with(this@InformationFragment)
                .load(args.imageBlock.userImageURL)
                .into(userImageView)

            tagsView.text = args.imageBlock.tags
            downloadsView.text = args.imageBlock.downloads.toString()
            likesView.text = args.imageBlock.likes.toString()
            usernameView.text = args.imageBlock.username
            viewsView.text = args.imageBlock.views.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}