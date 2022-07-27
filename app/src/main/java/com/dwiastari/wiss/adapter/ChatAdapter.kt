package com.dwiastari.wiss.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.*
import com.dwiastari.wiss.model.Message
import java.util.*

class ChatAdapter(val username: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    companion object{
        const val ITEM_SENT = 0
        const val ITEM_RECEIVE = 1
        const val ITEM_DATE_SENT = 2
        const val ITEM_DATE_RECEIVE = 3
    }
    
    val listMessage = arrayListOf<Message>()
    var showedDate = ""
    
    fun setData(listMessage: List<Message>){
        Log.d("setData", "setData")
        showedDate == ""
        this.listMessage.clear()
        this.listMessage.addAll(listMessage)
        notifyDataSetChanged()
    }
    
    override fun getItemViewType(position: Int): Int {
        val message = listMessage[position]
        Log.d("message", message.toString())
        Log.d("showedDate", "$showedDate ${message.date}")
        
        var returnType = -1
    
        if(username == message.senderId) {
            if(showedDate.isEmpty() || showedDate != message.date){
                showedDate = message.date.toString()
                returnType = ITEM_DATE_SENT
            } else {
                returnType = ITEM_SENT
            }
        } else {
            if(showedDate.isEmpty() || showedDate != message.date){
                showedDate = message.date.toString()
                returnType =ITEM_DATE_RECEIVE
            } else {
                returnType =ITEM_RECEIVE
            }
        }
        Log.d("returnType", returnType.toString())
        return returnType
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sent, parent, false)
                SentViewHolder(view)
            }
            ITEM_RECEIVE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receive, parent, false)
                ReceiveViewHolder(view)
            }
            ITEM_DATE_SENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_date_sent, parent, false)
                DateSentViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_date_receive, parent, false)
                DateReceiveViewHolder(view)
            }
        }
        
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listMessage[position]
        val locale = Locale("in", "ID")
        val calendar = Calendar.getInstance(locale)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        
        var dateText = ""
        
        if(showedDate.split("-")[1] == month.toString() && showedDate.split("-")[2] == year.toString()){
            if(showedDate.split("-")[0] == day.toString()){
                dateText = "Hari Ini"
            } else if (showedDate.split("-")[0].toInt() == day - 1){
                dateText = "Kemarin"
            } else {
                dateText = "$day ${listMonth[showedDate.split("-")[1].toInt()]} $year"
            }
        } else {
            dateText = "$day ${listMonth[showedDate.split("-")[1].toInt()]} $year"
        }
        
        when (holder.javaClass) {
            SentViewHolder::class.java -> {
                val viewHolder = holder as SentViewHolder
                viewHolder.binding.message.text = message.message
                viewHolder.binding.time.text = message.time
            }
            ReceiveViewHolder::class.java -> {
                val viewHolder = holder as ReceiveViewHolder
                viewHolder.binding.message.text = message.message
                viewHolder.binding.time.text = message.time
            }
            DateSentViewHolder::class.java -> {
                val viewHolder = holder as DateSentViewHolder
                viewHolder.binding.date.text = dateText
                viewHolder.binding.message.text = message.message
                viewHolder.binding.time.text = message.time
            }
            else -> {
                val viewHolder = holder as DateReceiveViewHolder
                viewHolder.binding.date.text = dateText
                viewHolder.binding.message.text = message.message
                viewHolder.binding.time.text = message.time
            }
        }
    }
    
    override fun getItemCount(): Int = listMessage.size
    
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatSentBinding.bind(itemView)
    }
    
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatReceiveBinding.bind(itemView)
    }
    
    class DateSentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatDateSentBinding.bind(itemView)
    }
    
    class DateReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemChatDateReceiveBinding.bind(itemView)
        
    }
    
    private val listMonth = arrayListOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November",
        "Desember")
    
}