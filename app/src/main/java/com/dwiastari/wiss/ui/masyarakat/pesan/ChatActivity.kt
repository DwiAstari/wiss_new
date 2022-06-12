package com.dwiastari.wiss.ui.masyarakat.pesan

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dwiastari.wiss.adapter.ChatAdapter
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityChatBinding
import com.dwiastari.wiss.model.*
import com.dwiastari.wiss.ui.masyarakat.profile.ProfileViewModel
import com.dwiastari.wiss.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var masyarakat: Masyarakat
    private val viewModel: ProfileViewModel by viewModels()
    private val listChat = arrayListOf<Message>()
    private lateinit var token: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        val receiverUsername = intent.getStringExtra("username")
        val receiverImage = intent.getStringExtra("image")
        val receiverName = intent.getStringExtra("name")
        getToken(receiverUsername!!)
        
        preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val currentUser = preferences.getString(Constant.KEY_USERNAME, "")
        val currentType = preferences.getString(Constant.KEY_TYPE, "")
        val currentName = preferences.getString(Constant.KEY_NAME, "")
    
        viewModel.onLoad(currentUser!!, currentType!!)
        
        viewModel.masyarakat.observe(this){
            masyarakat = it
        }
    
        val chatAdapter = ChatAdapter(currentUser!!)
        
        val firebase = FirebaseDatabase.getInstance()
        val db  = firebase.reference
        
        val roomSender = currentUser + receiverUsername
        val roomReceiver = receiverUsername + currentUser
        
        with(binding){
            btnbackL.setOnClickListener { finish() }
            tvReceiver.text = receiverName
            
            Glide.with(this@ChatActivity)
                .load(receiverImage)
                .into(ivReceiver)
            
            btnSend.setOnClickListener {
                val message = edtChat.text.toString()
                val messageObject = Message(message, currentUser!!)
                
                sendMessage(message, currentName!!, token)
                
                db.child("chat").child(roomSender).push()
                    .setValue(messageObject).addOnSuccessListener {
                        db.child("chat").child(roomReceiver).push()
                            .setValue(messageObject).addOnSuccessListener {
                                if(currentType == "masyarakat"){
                                    val masyarakatObject = ListMasyarakat(currentUser, masyarakat.foto, masyarakat.nama_masyarakat)
                                    db.child("konselor_room").child(receiverUsername!!).child(currentUser).setValue(masyarakatObject)
                                }
                            }
                    }
                
                edtChat.setText("")
            }
            
            rvChat.apply {
                adapter = chatAdapter
                val linearLayoutManager = LinearLayoutManager(this@ChatActivity)
                linearLayoutManager.stackFromEnd = true
                layoutManager = linearLayoutManager
                hasFixedSize()
            }
            
            db.child("chat").child(roomSender).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listChat.clear()
                    for(chatSnapshot in snapshot.children){
                        val message = chatSnapshot.getValue(Message::class.java)
                        listChat.add(message!!)
                    }
                    chatAdapter.setData(listChat)
                }
    
                override fun onCancelled(p0: DatabaseError) {
                    print("error")
                }
    
            })
            
        }
    }
    
    fun getToken(username: String){
        val apiClient = RetrofitClient().getInstance()
        apiClient.getToken(username).enqueue(object: Callback<DefaultResponse>{
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.message == null){
                        Toast.makeText(this@ChatActivity, "Penerima belum login, notifikasi tidak akan terkirim", Toast.LENGTH_SHORT).show()
                    } else {
                        token = response.body()?.message!!
                    }
                } else {
                    Toast.makeText(this@ChatActivity, "Gagal mendapatkan data penerima, notifikasi tidak akan terkirim", Toast.LENGTH_SHORT).show()
    
                }
            }
    
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(this@ChatActivity, "Gagal mendapatkan data penerima, notifikasi tidak akan terkirim", Toast.LENGTH_SHORT).show()
            }
    
        })
    }
    
    fun sendMessage(message: String, sender: String, token: String){
        val apiClient = RetrofitClient().getInstance()
        apiClient.sendMessage(sender, token, message, sender).enqueue(object: Callback<SendNotificationResponse>{
            override fun onResponse(call: Call<SendNotificationResponse>, response: Response<SendNotificationResponse>) {
            
            }
        
            override fun onFailure(call: Call<SendNotificationResponse>, t: Throwable) {
                Toast.makeText(this@ChatActivity, "Gagal mendapatkan data penerima, notifikasi tidak akan terkirim", Toast.LENGTH_SHORT).show()
            }
        
        })
    }
}