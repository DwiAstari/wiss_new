package com.dwiastari.wiss.ui.masyarakat.beranda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.dwiastari.wiss.databinding.FragmentBerandaBinding
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.ui.masyarakat.DashboardActivity
import com.dwiastari.wiss.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BerandaFragment : Fragment() {
    
    private lateinit var binding: FragmentBerandaBinding
    private val viewModel: BerandaViewModel by viewModels()
    private val listSlide = arrayListOf<Slide>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefences = requireActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val userType = prefences.getString(Constant.KEY_TYPE, "")
        
        viewModel.onLoad()
        viewModel.listSlide.observe(requireActivity()){
            listSlide.clear()
            listSlide.addAll(it)
            
            val imageList = arrayListOf<SlideModel>()
            
            for(slide in listSlide){
                imageList.add(SlideModel(slide.foto_slides))
            }
            
            binding.slider.apply {
                setImageList(imageList)
                setItemClickListener(object: ItemClickListener{
                    override fun onItemSelected(position: Int) {
                    
                    }
                })
            }
        }
        
        viewModel.kunjungan.observe(requireActivity()){
            binding.jmlKunjungan.text = it.toString()
        }
        
        viewModel.isLoading.observe(requireActivity()){
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        }
        
        viewModel.graphPayloadPengunjung.observe(requireActivity()){
            it.payload?.let { payload ->
                val count = payload.sumOf { it.total.toInt() }
                binding.jmlKunjungan.text = count.toString()
            }
        }
        
        val year = Calendar.getInstance().get(Calendar.YEAR)
        viewModel.getKunjungan(year.toString())
        
        binding.apply {
            btnInfo.setOnClickListener { requireActivity().startActivity(Intent(requireContext(), InformasiActivity::class.java)) }
            btnKegiatan.setOnClickListener { requireActivity().startActivity(Intent(requireContext(), MasyarakatKegiatanActivity::class.java)) }
            btnEbook.setOnClickListener { requireActivity().startActivity(Intent(requireContext(), EbookActivity::class.java)) }
            btnVideo.setOnClickListener { requireActivity().startActivity(Intent(requireContext(), VideoActivity::class.java)) }
            
            btnChat.setOnClickListener {
                (requireActivity() as DashboardActivity).openChat()
            }
        }
    }
   
}