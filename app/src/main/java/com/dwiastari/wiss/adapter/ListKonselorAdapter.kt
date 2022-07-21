package com.dwiastari.wiss.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemListKonselorBinding
import com.dwiastari.wiss.model.Konselor
import com.dwiastari.wiss.ui.admin.konselor.AddEditKonselorActivity

class ListKonselorAdapter :
    RecyclerView.Adapter<ListKonselorAdapter.ListViewHolder>() {
    private val mData = arrayListOf<Konselor>()
    private val mDataCopy = arrayListOf<Konselor>()
    
    fun setData(items: List<Konselor>) {
        mData.clear()
        mData.addAll(items)
        mDataCopy.clear()
        mDataCopy.addAll(items)
        this.notifyDataSetChanged()
    }
    
    fun filterData(text: String){
        mData.clear()
        if(text.isEmpty()){
            mData.addAll(mDataCopy)
        } else {
            for(konselor in mDataCopy){
                if(konselor.nama_akun.lowercase().contains(text.lowercase()) || konselor.bidang_konselor.lowercase().contains(text.lowercase())){
                    mData.add(konselor)
                }
            }
        }
        notifyDataSetChanged()
    }
    
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemListKonselorBinding.bind(itemView)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_konselor, parent, false)
        return ListViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val items = mData[position]
        val context = holder.itemView.context
        with(holder.binding) {
            tvLayananKonselor.text = "${position + 1}. ${items.bidang_konselor}"
            tvNamaKonselor.text = items.nama_akun
            
            if(items.foto.isNotEmpty()){
                Glide.with(context)
                    .load(items.foto)
                    .into(ivKonselor)
            }
        }
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddEditKonselorActivity::class.java)
            intent.putExtra("konselor", items)
            context.startActivity(intent)
        }
    }
    
    override fun getItemCount(): Int {
        return mData.size
    }
}