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
import com.dwiastari.wiss.model.Ebook
import com.dwiastari.wiss.model.Video

class ListEbookAdapter :
    RecyclerView.Adapter<ListEbookAdapter.ListViewHolder>() {
    private val mData = ArrayList<Ebook>()
    private lateinit var itemListener: ItemListener
    
    fun setData(items: ArrayList<Ebook>) {
        mData.clear()
        mData.addAll(items)
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
        fun bind(items: Ebook, position: Int) {
            with(itemView) {
                binding.tvJudul.text = "$position. ${items.judul_ebook}"
                binding.tvLink.text = items.link_ebook
                binding.btnDelete.setOnClickListener { itemListener.onDelete(items.id_ebook) }
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
        holder.bind(mData[position], position)
        holder.itemView.apply {
            setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(mData[position].link_ebook)
                context.startActivity(intent)
            }
        }
    }
    
    override fun getItemCount(): Int {
        return mData.size
    }
    
    interface ItemListener {
        fun onDelete(id: String)
        fun onEdit(ebook: Ebook)
    }
}