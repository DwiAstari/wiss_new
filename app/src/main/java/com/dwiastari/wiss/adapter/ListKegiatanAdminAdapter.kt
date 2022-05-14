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
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.ui.admin.kegiatan.EditKegiatanActivity

class ListKegiatanAdminAdapter :
    RecyclerView.Adapter<ListKegiatanAdminAdapter.ListViewHolder>() {
    private val mData = ArrayList<Artikel>()

    fun setData(items: ArrayList<Artikel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemArtikelBinding.bind(itemView)
        fun bind(items: Artikel) {
            with(itemView) {
                binding.judulartikel.text = items.judul_artikel
                binding.tanggalartikel.text = items.tanggal_artikel
                binding.isiartikel.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(items.isi_artikel, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(items.isi_artikel)
                }
//                binding.areaartikel.text = items.area
//                binding.penulis.text = items.penulis
                
                Glide.with(context)
                    .load(items.foto_kegiatan)
                    .placeholder(R.color.background)
                    .centerCrop()
                    .into(binding.ivArtikel)
                
                setOnClickListener{
                    val intent = Intent(context, EditKegiatanActivity::class.java)
                    intent.putExtra(EditKegiatanActivity.ARTIKEL_EXTRA, items)
                    context.startActivity(intent)
                }
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