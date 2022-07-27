package com.dwiastari.wiss.ui.masyarakat.pesan

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.adapter.ListPesanAdapter
import com.dwiastari.wiss.databinding.FragmentPesanBinding
import com.dwiastari.wiss.model.ListMasyarakat
import com.dwiastari.wiss.model.PesanModel
import com.dwiastari.wiss.utils.Constant
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PesanFragment : Fragment() {
    private lateinit var binding: FragmentPesanBinding
    private lateinit var listPesanAdapter: ListPesanAdapter
    private lateinit var prefences: SharedPreferences
    private val listPesan = arrayListOf<PesanModel>()
    private val viewModel: PesanViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesanBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        val firebase = FirebaseDatabase.getInstance()
        val db = firebase.reference
        
        listPesanAdapter = ListPesanAdapter()
        
        binding.rvChat.apply {
            adapter = listPesanAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }
        
        prefences = requireActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val userType = prefences.getString(Constant.KEY_TYPE, "")
        val username = prefences.getString(Constant.KEY_USERNAME, "")
        
        if(userType == "masyarakat"){
            with(viewModel){
                getKonselorList()
                
                isLoading.observe(requireActivity()){
                    binding.loading.visibility = if(it) View.VISIBLE else View.GONE
                }
                
                listKonselor.observe(requireActivity()){
                    listPesan.clear()
                    it.forEach{ konselor ->
                        var newNotif: Boolean? = null
                        db.child("masyarakat_new_notif").child(username!!).child(konselor.username).addValueEventListener(object: ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                newNotif = dataSnapshot.child("newNotif").value as Boolean?
                                listPesan.add(PesanModel(konselor.foto, konselor.bidang_konselor, konselor.nama_akun, konselor.username, newNotif?:false, 0))
                                listPesanAdapter.setData(listPesan, ListPesanAdapter.MASYARAKAT)
                            }
    
                            override fun onCancelled(p0: DatabaseError) {
                            
                            }
    
                        })
                    }
                }
            }
        } else {
            
            db.child("konselor_room").child(username!!).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listPesan.clear()
                    for(pesanSnapshot in snapshot.children){
                        val masyarakat = pesanSnapshot.getValue(ListMasyarakat::class.java)
                        masyarakat?.let {
                            listPesan.add(PesanModel(masyarakat.image!!, masyarakat.name!!, it.lastMessage!!, masyarakat.username!!, it.newNotif!!, it
                                .timeMillis!!))
                        }
                        
                    }
                    val sortedListPesan = listPesan.sortedByDescending { it.timeInMillis }
                    listPesanAdapter.setData(listPesan, ListPesanAdapter.KONSELOR)
                }
    
                override fun onCancelled(p0: DatabaseError) {
                
                }
    
            })
        }
    }
    
}