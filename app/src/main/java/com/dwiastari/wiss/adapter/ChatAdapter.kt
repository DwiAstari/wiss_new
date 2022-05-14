package com.dwiastari.wiss.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ItemChatReceiveBinding
import com.dwiastari.wiss.databinding.ItemChatSentBinding
import com.dwiastari.wiss.model.Message

class ChatAdapter(val username: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    companion object{
        const val ITEM_SENT = 0
        const val ITEM_RECEIVE = 1
    }
    
    val listMessage = arrayListOf<Message>()
    
    fun setData(listMessage: List<Message>){
        this.listMessage.clear()
        this.listMessage.addAll(listMessage)
        notifyDataSetChanged()
    }
    
    override fun getItemViewType(position: Int): Int {
        val message = listMessage[position]
        return if(username == message.senderId)
            ITEM_SENT
        else
            ITEM_RECEIVE
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sent, parent, false)
            SentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receive, parent, false)
            ReceiveViewHolder(view)
        }
        
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listMessage[position]
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.message.text = message.message
        } else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.binding.message.text = message.message
        }
    }
    
    override fun getItemCount(): Int = listMessage.size
    
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatSentBinding.bind(itemView)
        
    }
    
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatReceiveBinding.bind(itemView)
    }
    
}