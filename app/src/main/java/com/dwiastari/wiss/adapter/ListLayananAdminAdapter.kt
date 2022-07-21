package com.dwiastari.wiss.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemLayananBinding
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.model.Layanan
import com.dwiastari.wiss.ui.admin.layanan.EditLayananActivity

class ListLayananAdminAdapter :
        RecyclerView.Adapter<ListLayananAdminAdapter.ListViewHolder>() {
    private  val mData = ArrayList<Layanan>()
    private val mDataCopy = arrayListOf<Layanan>()

    fun setData(items: ArrayList<Layanan>){
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
            for(layanan in mDataCopy){
                if(layanan.layanan.lowercase().contains(text.lowercase())){
                    mData.add(layanan)
                }
            }
        }
        notifyDataSetChanged()
    }

   inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val binding = ItemLayananBinding.bind(itemView)
       fun bind(items: Layanan) {
           with(itemView) {
               binding.hariLayanan.text = "${items.id_hari}. ${items.hari}"
               binding.isiLayanan.text = items.layanan
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
        val layanan = mData[position]
        
        holder.itemView.apply {
            setOnClickListener {
                val intent = Intent(context, EditLayananActivity::class.java)
                intent.putExtra(EditLayananActivity.EXTRA_LAYANAN, layanan)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}