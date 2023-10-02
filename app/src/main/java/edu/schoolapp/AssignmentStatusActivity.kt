package edu.schoolapp

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import edu.schoolapp.model.AssignmentModel

class AssignmentStatusActivity : AppCompatActivity() {
    private lateinit var statusPieChart: PieChart
    private lateinit var assignmentList: List<AssignmentModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_status)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        assignmentList = SchoolApp.data as List<AssignmentModel>
        statusPieChart = findViewById(R.id.status_pie_chart)
        statusPieChart.setUsePercentValues(true)
        addData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selectedItemId = item.itemId
        if (selectedItemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun addData() {
        val entries: MutableList<PieEntry> = ArrayList()
        var totalMark = 0.0f
        var totalMarkObtained = 0.0f
        for (model in assignmentList) {
            totalMark += model.mark.toFloat()
            totalMarkObtained += model.markObtained.toFloat()
        }
        val totalMarkObtainedPercentage = totalMarkObtained / totalMark * 100.0f / 100.0f
        val totalMarkPercentage = 1.0f - totalMarkObtainedPercentage
        entries.add(PieEntry(totalMarkPercentage, 0.0f))
        entries.add(PieEntry(totalMarkObtainedPercentage, 0.0f))
        val dataSet = PieDataSet(entries, "Assignment Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        val colors: MutableList<Int> = ArrayList()
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.WHITE)
        statusPieChart.data = data
    }
}