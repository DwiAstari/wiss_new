package com.dwiastari.wiss.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemVideoBinding
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.model.Video

class ListVideoAdapter :
    RecyclerView.Adapter<ListVideoAdapter.ListViewHolder>() {
    private val mData = ArrayList<Video>()
    private val mDataCopy = arrayListOf<Video>()
    private lateinit var itemListener: ItemListener
    
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
    
    fun setListener(itemListener: ItemListener){
        this.itemListener = itemListener
    }
    
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.color.background)
            .centerCrop()
            .into(this)
    }
    
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemVideoBinding.bind(itemView)
        fun bind(items: Video, position: Int) {
            with(itemView) {
                binding.tvJudul.text = "$position. ${items.judul_video}"
                binding.tvLink.text = items.link_video
                binding.btnDelete.setOnClickListener { itemListener.onDelete(items.id_video) }
                binding.btnEdit.setOnClickListener { itemListener.onEdit(items) }
            }
            
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false )
        return ListViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position], position + 1)
        holder.itemView.apply {
            setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(mData[position].link_video)
                context.startActivity(intent)
            }
        }
    }
    
    override fun getItemCount(): Int {
        return mData.size
    }
    
    interface ItemListener {
        fun onDelete(id: String)
        fun onEdit(video: Video)
    }
}