package com.dwiastari.wiss.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemSlideBinding
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.ui.admin.slide.DetailSlideAdminActivity

class ListSlideAdminAdapter :
        RecyclerView.Adapter<ListSlideAdminAdapter.ListViewHolder>() {
    private val mData = ArrayList<Slide>()

    fun setData(items: ArrayList<Slide>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.color.background)
            .centerCrop()
            .into(this)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSlideBinding.bind(itemView)
        fun bind(items: Slide, position: Int) {
            with(itemView) {
                binding.tvJudul.text = "$position. ${items.judul_slide}"
                binding.imgSlides.loadImage(items.foto_slides)
            }
        }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false )
            return ListViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.bind(mData[position], position + 1)
            holder.itemView.apply {
                setOnClickListener {
                    val intent = Intent(context, DetailSlideAdminActivity::class.java)
                    intent.putExtra(DetailSlideAdminActivity.EXTRA_SLIDE, mData[position])
                    context.startActivity(intent)
                }
            }
        }

        override fun getItemCount(): Int {
            return mData.size
        }
    }