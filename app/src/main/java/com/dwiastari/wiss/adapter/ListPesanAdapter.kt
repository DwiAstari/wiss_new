package com.dwiastari.wiss.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemPesanBinding
import com.dwiastari.wiss.model.Konselor
import com.dwiastari.wiss.model.PesanModel
import com.dwiastari.wiss.ui.masyarakat.pesan.ChatActivity

class ListPesanAdapter: RecyclerView.Adapter<ListPesanAdapter.ViewHolder>() {
    
    companion object {
        const val MASYARAKAT = 0
        const val KONSELOR = 1
    }
    
    private val listPesan = arrayListOf<PesanModel>()
    private var type = 0
   
    fun setData(listPesan: List<PesanModel>, type: Int){
        this.listPesan.clear()
        this.listPesan.addAll(listPesan)
        this.type = type
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPesanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesan, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ListPesanAdapter.ViewHolder, position: Int) {
        val pesan = listPesan[position]
        holder.bind(pesan, type)
    }
    
    override fun getItemCount(): Int = listPesan.size
    
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPesanBinding.bind(itemView)
        
        fun bind(pesan: PesanModel, type: Int){
            
            with(itemView){
                var name = ""
                
                if(type == MASYARAKAT) {
                    binding.tv2.setTextColor(Color.parseColor("#000000"))
                    name = pesan.text2
                }
                else {
                    binding.tv1.setTextColor(Color.parseColor("#000000"))
                    name = pesan.text1
                }
                
                binding.tv1.text = pesan.text1
                binding.tv2.text = pesan.text2
                
                if(pesan.image.isNullOrEmpty()){
                
                } else {
                    Glide.with(context)
                        .load(pesan.image)
                        .into(binding.imgPesan)
                }
                
                
                setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("username", pesan.username)
                    intent.putExtra("image", pesan.image)
                    intent.putExtra("name", name)
                    context.startActivity(intent)
                }
            }
        }
    }
    
    
}