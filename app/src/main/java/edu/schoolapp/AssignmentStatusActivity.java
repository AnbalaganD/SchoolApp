package edu.schoolapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.AssignmentModel;

public class AssignmentStatusActivity extends AppCompatActivity {

    private PieChart statusPieChart;
    private List<AssignmentModel> assignmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignmentList = (List<AssignmentModel>) SchoolApp.data;

        statusPieChart = findViewById(R.id.status_pie_chart);
        statusPieChart.setUsePercentValues(true);
        addData();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void addData() {
        List<PieEntry> entries = new ArrayList<PieEntry>();

        float totalMark = 0.0f;
        float totalMarkObtained = 0.0f;

        for (AssignmentModel model : assignmentList) {
            totalMark += model.getMark();
            totalMarkObtained += model.getMarkObtained();
        }

        float totalMarkObtainedPercentage = (totalMarkObtained / totalMark * 100.0f) / 100.0f;
        float totalMarkPercentage = 1.0f - totalMarkObtainedPercentage;

        entries.add(new PieEntry(totalMarkPercentage, 0.0f));
        entries.add(new PieEntry(totalMarkObtainedPercentage, 0.0f));

        PieDataSet dataSet = new PieDataSet(entries, "Assignment Result");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        List<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);
        statusPieChart.setData(data);
    }
}
