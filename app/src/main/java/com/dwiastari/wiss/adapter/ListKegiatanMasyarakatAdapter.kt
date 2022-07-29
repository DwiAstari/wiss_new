package com.dwiastari.wiss.adapter

import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemArtikelBinding
import com.dwiastari.wiss.databinding.ItemMasyarakatKegiatanBinding
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.ui.admin.kegiatan.EditKegiatanActivity

class ListKegiatanMasyarakatAdapter :
    RecyclerView.Adapter<ListKegiatanMasyarakatAdapter.ListViewHolder>() {
    private val mData = ArrayList<Artikel>()
    private val mDataCopy = arrayListOf<Artikel>()

    fun setData(items: ArrayList<Artikel>) {
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
            for(artikel in mDataCopy){
                if(artikel.judul_artikel.lowercase().contains(text.lowercase())){
                    mData.add(artikel)
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMasyarakatKegiatanBinding.bind(itemView)
        fun bind(items: Artikel) {
            with(itemView) {
                binding.judulartikel.text = items.judul_artikel
                binding.tvIsiArtikel.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(items.isi_artikel, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(items.isi_artikel)
                }
                
                Glide.with(context)
                    .load(items.foto_kegiatan)
                    .placeholder(R.color.background)
                    .centerCrop()
                    .into(binding.ivArtikel)
                
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_masyarakat_kegiatan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}