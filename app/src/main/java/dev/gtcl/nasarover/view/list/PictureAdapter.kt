package dev.gtcl.nasarover.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.gtcl.nasarover.EspressoIdlingResource
import dev.gtcl.nasarover.databinding.ItemPhotoBinding
import dev.gtcl.nasarover.model.Photo

class PictureAdapter(private val photoClickListener: PhotoClickListener): ListAdapter<Photo, PictureAdapter.PhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  =
        PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), photoClickListener)
    }

    override fun submitList(list: List<Photo>?) {
        EspressoIdlingResource.increment()
        super.submitList(list) { EspressoIdlingResource.decrement() }
    }

    // View Holder
    class PhotoViewHolder(private var binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, photoClickListener: PhotoClickListener) {
            binding.photo = photo
            binding.cardView.setOnClickListener { photoClickListener.onClick(photo) }
            binding.executePendingBindings()
        }
    }

    // Diff Callback
    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem === newItem
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id
    }

    // OnClickListener
    interface PhotoClickListener{
        fun onClick(photo: Photo)
    }
}