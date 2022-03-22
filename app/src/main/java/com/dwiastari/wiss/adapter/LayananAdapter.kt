package com.dwiastari.wiss.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R

class LayananAdapter(private val list: ArrayList<LayananAdapter>): RecyclerView.Adapter<LayananAdapter.LayananViewHolder>(){
    inner class LayananViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    fun bind(Lay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layanan,parent,false)
        return LayananViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}