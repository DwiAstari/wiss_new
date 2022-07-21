package com.dwiastari.wiss.adapter

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemMasyarakatEbookBinding
import com.dwiastari.wiss.model.Ebook
import com.dwiastari.wiss.model.Video

class ListMasyarakatVideoAdapter :
    RecyclerView.Adapter<ListMasyarakatVideoAdapter.ListViewHolder>() {
    private val mData = ArrayList<Video>()
    private val mDataCopy = arrayListOf<Video>()
    
    fun setData(items: ArrayList<Video>) {
        mData.clear()
        mData.addAll(items)
        mDataCopy.clear()
        mDataCopy.addAll(items)
        notifyDataSetChanged()
    }
    
    fun filterData(text: String){
        mData.clear()
        if(text.isEmpty()){
            mData.addAll(mDataCopy)
        } else {
            for(video in mDataCopy){
                if(video.judul_video.lowercase().contains(text.lowercase())){
                    mData.add(video)
                }
            }
        }
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
        private val binding = ItemMasyarakatEbookBinding.bind(itemView)
        fun bind(items: Video, position: Int) {
            with(itemView) {
                binding.tvJudul.text = "$position. ${items.judul_video}"
                binding.btnOpen.text = "Buka Video"
                binding.btnOpen.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(mData[position].link_video)
                    context.startActivity(intent)
                }
            }
            
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_masyarakat_ebook, parent, false )
        return ListViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position], position + 1)
    }
    
    override fun getItemCount(): Int {
        return mData.size
    }
}