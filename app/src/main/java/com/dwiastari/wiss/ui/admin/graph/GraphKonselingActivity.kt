package com.dwiastari.wiss.ui.admin.graph

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityGraphKonselingBinding
import com.dwiastari.wiss.model.PayloadKonseling
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphKonselingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGraphKonselingBinding
    private lateinit var title: String
    private val viewModel: GraphKonselingViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphKonselingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        with(viewModel){
            isLoading.observe(this@GraphKonselingActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
    
            listYearResponse.observe(this@GraphKonselingActivity){
                it.payload?.let {
                    initDropdownYear(it)
                }
            }
            
            graphPayload.observe(this@GraphKonselingActivity){
                it.payload?.let {
                    initBarChart(it)
                }
            }
    
            errorMessage.observe(this@GraphKonselingActivity) {
                Toast.makeText(this@GraphKonselingActivity, it, Toast.LENGTH_SHORT).show()
            }
            
            getData()
        }
    
        with(binding){
//            val barDataSet = b
            chart.apply {
                setDrawBarShadow(false)
                setDrawValueAboveBar(true)
            
                description.isEnabled = false
            
                setPinchZoom(true)
                setDrawGridBackground(false)
            
                val xAxis = xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
    
                val l = chart.legend
                l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                l.orientation = Legend.LegendOrientation.VERTICAL
                l.setDrawInside(true)
                l.yOffset = 10f
                l.xOffset = 10f
                l.yEntrySpace = 0f
                l.textSize = 8f
                l.isWordWrapEnabled = true
            }
        
            btnbackL.setOnClickListener {
                finish()
            }
        }
    }
    
    private fun initDropdownYear(listYear: List<String>){
        val sortedListYear = listYear.sortedBy { it.toInt() }
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_role, sortedListYear)
        binding.dropdownSelectYear.apply {
            setAdapter(adapter)
            setOnItemClickListener { parent, view, position, id ->
                viewModel.getKonseling(sortedListYear[position])
                title = "Kunjungan tahun ${sortedListYear[position]}"
            }
        }
    }
    
    private fun initBarChart(listKonseling: List<PayloadKonseling>){
        with(binding){
            val groupSpace = 0.08f
            val barSpace = 0.015f // x8 DataSet
            val barWidth = 0.1f // x48DataSet
    
            val value1 = arrayListOf<BarEntry>()
            val value2 = arrayListOf<BarEntry>()
            val value3 = arrayListOf<BarEntry>()
            val value4 = arrayListOf<BarEntry>()
            val value5 = arrayListOf<BarEntry>()
            val value6 = arrayListOf<BarEntry>()
            val value7= arrayListOf<BarEntry>()
            val value8 = arrayListOf<BarEntry>()
    
            val listBidang = arrayOf("Layanan Konseling Pranikah", "Layanan Konseling KB dan Kes. Reproduksi", "Layanan Keluarga Remaja da Remaja", "Layanan " +
                    "Konseling Keluarga Lansia dan Lansia", "Pembinaan Usaha Ekonomi Keluarga", "Layanan Konseling Keluarga Balita dan Anak", "Layanan Informasi " +
                    "Kependudukan dan KB", "Layanan Konseling Keluarga Harmonis")
            
            val labelName = arrayListOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")
            labelName.reverse()
            
            for(i in labelName.indices){
                val listKonselingFilter = listKonseling.filter { it.month == labelName[i] }
                var jumlah1 = 0f
                var jumlah2 = 0f
                var jumlah3 = 0f
                var jumlah4 = 0f
                var jumlah5 = 0f
                var jumlah6 = 0f
                var jumlah7 = 0f
                var jumlah8 = 0f
                
                listKonselingFilter.forEach{ konseling ->
                    when(konseling.jenis){
                        listBidang[0] -> {
                            jumlah1 = konseling.jumlah.toFloat()
                        }
                        listBidang[1] -> {
                            jumlah2 = konseling.jumlah.toFloat()
                        }
                        listBidang[2] -> {
                            jumlah3 = konseling.jumlah.toFloat()
                        }
                        listBidang[3] -> {
                            jumlah4 = konseling.jumlah.toFloat()
                        }
                        listBidang[4] -> {
                            jumlah5 = konseling.jumlah.toFloat()
                        }
                        listBidang[5] -> {
                            jumlah6 = konseling.jumlah.toFloat()
                        }
                        listBidang[6] -> {
                            jumlah7 = konseling.jumlah.toFloat()
                        }
                        listBidang[7] -> {
                            jumlah8 = konseling.jumlah.toFloat()
                        }
        
                    }
                }
                
                value1.add(BarEntry(i.toFloat(), jumlah1))
                value2.add(BarEntry(i.toFloat(), jumlah2))
                value3.add(BarEntry(i.toFloat(), jumlah3))
                value4.add(BarEntry(i.toFloat(), jumlah4))
                value5.add(BarEntry(i.toFloat(), jumlah5))
                value6.add(BarEntry(i.toFloat(), jumlah6))
                value7.add(BarEntry(i.toFloat(), jumlah7))
                value8.add(BarEntry(i.toFloat(), jumlah8))
            }
            
//            listKonseling.forEach{ konseling ->
//                for(i in labelName.indices){
//                    if(konseling.month == labelName[i]){
//                        when(konseling.jenis){
//                            listBidang[0] -> {
//                                value1.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[1] -> {
//                                value2.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[2] -> {
//                                value3.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[3] -> {
//                                value4.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[4] -> {
//                                value5.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[5] -> {
//                                value6.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[6] -> {
//                                value7.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//                            listBidang[7] -> {
//                                value8.add(BarEntry(i.toFloat(), konseling.jumlah.toFloat()))
//                            }
//
//                        }
//
//                        break
//                    }
//                }
//            }
    
            val set1 = BarDataSet(value1, listBidang[0])
            set1.color = Color.parseColor("#faa259")
            val set2 = BarDataSet(value2, listBidang[1])
            set2.color = Color.parseColor("#ef5c7f")
            val set3 = BarDataSet(value3, listBidang[2])
            set3.color = Color.parseColor("#91eb80")
            val set4 = BarDataSet(value4, listBidang[3])
            set4.color = Color.parseColor("#e5d652")
            val set5 = BarDataSet(value5, listBidang[4])
            set5.color = Color.parseColor("#259494")
            val set6 = BarDataSet(value6, listBidang[5])
            set6.color = Color.parseColor("#444347")
            val set7 = BarDataSet(value7, listBidang[6])
            set7.color = Color.parseColor("#7cb6e5")
            val set8 = BarDataSet(value8, listBidang[7])
            set8.color = Color.parseColor("#8085e7")
    
            val data = BarData(set8, set7, set6, set5, set4, set3, set2, set1)
            data.setValueFormatter(object: ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    val returnValue = value.toInt()
                    if(returnValue == 0){
                        return ""
                    }
                    return returnValue.toString()
                }
            })
            chart.data = data
    
            val xAxis: XAxis = chart.xAxis
            xAxis.granularity = 1f
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            xAxis.labelCount = labelName.size
            xAxis.valueFormatter = IndexAxisValueFormatter(labelName)
            xAxis.setCenterAxisLabels(true);
    
    
            val leftAxis: YAxis = chart.axisLeft
            leftAxis.valueFormatter = LargeValueFormatter()
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 35f
            leftAxis.axisMinimum = 0f
//            leftAxis.isInverted = true
    
            chart.axisRight.isEnabled = false
    
            chart.animateY(1000);
            // specify the width each bar should have
    
            // specify the width each bar should have
            chart.barData.barWidth = barWidth
    
            // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
    
            chart.groupBars(0f, groupSpace, barSpace)
            chart.extraBottomOffset = 10f
            chart.invalidate()
        }
    }
}