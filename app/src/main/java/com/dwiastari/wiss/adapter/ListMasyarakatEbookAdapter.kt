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
import com.dwiastari.wiss.databinding.ItemMasyarakatEbookBinding
import com.dwiastari.wiss.model.Ebook

class ListMasyarakatEbookAdapter : RecyclerView.Adapter<ListMasyarakatEbookAdapter.ListViewHolder>() {
    private val mData = ArrayList<Ebook>()
    private val mDataCopy = arrayListOf<Ebook>()
    
    
    fun setData(items: ArrayList<Ebook>) {
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
            for(ebook in mDataCopy){
                if(ebook.judul_ebook.lowercase().contains(text.lowercase())){
                    mData.add(ebook)
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
        fun bind(items: Ebook, position: Int) {
            with(itemView) {
                binding.tvJudul.text = "$position. ${items.judul_ebook}"
                binding.btnOpen.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(mData[position].link_ebook)
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