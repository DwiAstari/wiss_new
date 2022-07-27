package com.dwiastari.wiss.ui.admin.graph

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityGraphPengunjungBinding
import com.dwiastari.wiss.model.GraphPayload
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphPengunjungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGraphPengunjungBinding
    private val viewModel: GraphPengunjungViewModel by viewModels()
    private lateinit var title: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphPengunjungBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        with(viewModel){
            isLoading.observe(this@GraphPengunjungActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            listYearResponse.observe(this@GraphPengunjungActivity){
                it.payload?.let {
                    initDropdownYear(it)
                }
            }
            
            graphPayload.observe(this@GraphPengunjungActivity) {
                it.payload?.let {
                    initBarChart(it)
                }
            }
            
            errorMessage.observe(this@GraphPengunjungActivity) {
                Toast.makeText(this@GraphPengunjungActivity, it, Toast.LENGTH_SHORT).show()
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
                
            }
            
            btnbackL.setOnClickListener {
                finish()
            }
        }
    }
    
    private fun initDropdownYear(listYear: List<String>){
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_role, listYear)
        binding.dropdownSelectYear.apply {
            setAdapter(adapter)
            setOnItemClickListener { parent, view, position, id ->
                viewModel.getKunjungan(listYear[position])
                title = "Kunjungan tahun ${listYear[position]}"
            }
        }
    }
    
    private fun initBarChart(listPayload: List<GraphPayload>){
        with(binding){
            val listBarEntry = arrayListOf<BarEntry>()
            val labelName = arrayListOf<String>()
    
            for(i in listPayload.indices){
                labelName.add(listPayload[i].month)
                listBarEntry.add(BarEntry(i.toFloat(), listPayload[i].total.toFloat()))
            }
    
            val dataSet = BarDataSet(listBarEntry, title)
            dataSet.color = ColorTemplate.getHoloBlue()
            
            
    
            val barData = BarData(dataSet)
            barData.setValueFormatter(object: ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    val returnValue = value.toInt()
                    if(returnValue == 0){
                        return ""
                    }
                    return returnValue.toString()
                }
            })
            chart.data = barData
    
            val xAxis: XAxis = chart.xAxis
            xAxis.granularity = 1f
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.labelCount = labelName.size
            xAxis.valueFormatter = IndexAxisValueFormatter(labelName)
    
            val leftAxis: YAxis = chart.axisLeft
            leftAxis.valueFormatter = LargeValueFormatter()
            leftAxis.setDrawGridLines(false)
            leftAxis.spaceTop = 35f
            leftAxis.axisMinimum = 0f
            
            chart.axisRight.isEnabled = false
    
            chart.animateY(1000);
            chart.invalidate();
        }
    }
    
}