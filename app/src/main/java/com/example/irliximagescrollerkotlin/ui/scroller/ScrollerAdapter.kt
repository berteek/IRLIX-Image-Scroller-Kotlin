package com.example.irliximagescrollerkotlin.ui.scroller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.databinding.ImageBlockBinding

class ScrollerAdapter(private val listener: OnImageBlockClickListener) :
    RecyclerView.Adapter<ScrollerAdapter.ImageBlockViewHolder>() {

    private var imageBlocks: List<ImageBlock> = emptyList()

    inner class ImageBlockViewHolder(private val binding: ImageBlockBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                val imageBlock = imageBlocks[position]
                listener.onImageBlockClick(imageBlock)
            }
        }

        fun bindViews(imageBlock: ImageBlock) {
            binding.apply {
                Glide.with(cardView)
                    .load(imageBlock.imageURL)
                    .into(imageView)
                usernameView.text = imageBlock.username.replaceFirstChar { it.uppercase() }
                likesTextView.text = imageBlock.likes.toString()
                tagsView.text = imageBlock.tags
                Glide.with(cardView)
                    .load(imageBlock.userImageURL)
                    .into(userImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageBlockViewHolder {
        return ImageBlockViewHolder(ImageBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageBlockViewHolder, position: Int) {
        val currentImageBlock = imageBlocks[position]
        holder.bindViews(currentImageBlock)
    }

    override fun getItemCount(): Int = imageBlocks.size

    fun setImageBlocks(imageBlocks: List<ImageBlock>) {
        this.imageBlocks = imageBlocks
        notifyDataSetChanged()
    }

    interface OnImageBlockClickListener {
        fun onImageBlockClick(imageBlock: ImageBlock)
    }
}