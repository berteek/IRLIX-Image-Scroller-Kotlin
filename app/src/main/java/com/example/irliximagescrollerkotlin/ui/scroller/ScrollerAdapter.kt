package com.example.irliximagescrollerkotlin.ui.scroller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.irliximagescrollerkotlin.data.ImageBlock
import com.example.irliximagescrollerkotlin.databinding.ImageBlockBinding

class ScrollerAdapter: RecyclerView.Adapter<ScrollerAdapter.ImageBlockViewHolder>() {

    private var imageBlocks: List<ImageBlock>? = emptyList()

    class ImageBlockViewHolder(private val binding: ImageBlockBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imageBlock: ImageBlock?) {
            binding.apply {
                Glide.with(cardView)
                    .load(imageBlock?.imageURL)
                    .into(imageView)
                usernameView.text = imageBlock!!.username.replaceFirstChar { it.uppercase() }
                likesTextView.text = imageBlock!!.likes.toString()
                tagsView.text = imageBlock!!.tags
                Glide.with(cardView)
                    .load(imageBlock!!.userImageURL)
                    .into(userImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageBlockViewHolder {
        return ImageBlockViewHolder(ImageBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageBlockViewHolder, position: Int) {
        val currentImageBlock = imageBlocks?.get(position)
        holder.bind(currentImageBlock)
    }

    override fun getItemCount(): Int {
        val nullableSize: Int? = imageBlocks?.size

        return if (nullableSize != null) {
            val size: Int = nullableSize
            size
        } else {
            0
        }
    }

    fun setImageBlocks(imageBlocks: List<ImageBlock>?) {
        this.imageBlocks = imageBlocks
        notifyDataSetChanged()
    }
}