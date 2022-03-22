package com.dwiastari.wiss.ui.admin.layanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemLayananBinding
import com.dwiastari.wiss.model.Layanan

class ListLayananAdminAdapter :
        RecyclerView.Adapter<ListLayananAdminAdapter.ListViewHolder>() {
            private  val mData = ArrayList<Layanan>()

            fun setData(items: ArrayList<Layanan>){
                mData.clear()
                mData.addAll(items)
                notifyDataSetChanged()
            }

           inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
               private val binding = ItemLayananBinding.bind(itemView)
               fun bind(items: Layanan) {
                   with(itemView) {
                       binding.hari.text = items.hari
                       binding.layanan.text = items.layanan
                   }
               }
           }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View=
            LayoutInflater.from(parent.context).inflate(R.layout.item_layanan, parent, false)
            return ListViewHolder(view)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}