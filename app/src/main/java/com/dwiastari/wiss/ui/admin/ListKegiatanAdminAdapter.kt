package com.dwiastari.wiss.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemArtikelBinding
import com.dwiastari.wiss.model.Artikel

class ListKegiatanAdminAdapter :
    RecyclerView.Adapter<ListKegiatanAdminAdapter.ListViewHolder>() {
    private val mData = ArrayList<Artikel>()

    fun setData(items: ArrayList<Artikel>) {
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
        private val binding = ItemArtikelBinding.bind(itemView)
        fun bind(items: Artikel) {
            with(itemView) {
                binding.judulartikel.text = items.judul_artikel
                binding.tanggalartikel.text = items.tanggal_artikel
                binding.isiartikel.text = items.isi_artikel
                binding.areaartikel.text = items.area
                binding.profileIv.loadImage(items.foto_kegiatan)
                binding.penulis.text = items.penulis
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_artikel, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}