//package com.dwiastari.wiss.ui.admin.video
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.dwiastari.wiss.R
//import com.dwiastari.wiss.databinding.ItemSlideBinding
//import com.dwiastari.wiss.model.Slide
//import com.dwiastari.wiss.ui.admin.slide.ListSlideAdminAdapter
//
//class ListVideoAdminAdapter :
//    RecyclerView.Adapter<ListSlideAdminAdapter.ListViewHolder>() {
//    private val mData = ArrayList<Slide>()
//
//    fun setData(items: ArrayList<Slide>) {
//        mData.clear()
//        mData.addAll(items)
//        notifyDataSetChanged()
//    }
//
//    fun ImageView.loadImage(url: String?) {
//        Glide.with(this.context)
//            .load(url)
//            .placeholder(R.color.background)
//            .centerCrop()
//            .into(this)
//    }
//
//    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val binding = ItemSlideBinding.bind(itemView)
//        fun bind(items: Slide) {
//            with(itemView) {
//                binding.judulSlide.text = items.judul_slide
//                binding.fotoSlide.loadImage(items.foto_slides)
//                binding.status.text = items.status
//                binding.noUrut.text = items.no_urut
//            }
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false )
//        return ListViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        holder.bind(mData[position])
//    }
//
//    override fun getItemCount(): Int {
//        return mData.size
//    }
//}