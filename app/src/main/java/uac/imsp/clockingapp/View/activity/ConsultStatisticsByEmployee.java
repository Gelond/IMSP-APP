package uac.imsp.clockingapp.View.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import uac.imsp.clockingapp.Controller.control.ConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByEmployeeView;

public class ConsultStatisticsByEmployee extends AppCompatActivity
 implements IConsultStatisticsByEmployeeView,
        View.OnClickListener {
    private IConsultStatisticsByEmployeeController consultStatisticsByEmployeePresenter;
    private TextView reportPeriod;
    Button next,previous;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_statistics_by_employee);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.menu_statistics_by_employee);
       initView();

        int actionNumber = getIntent().getIntExtra("ACTION_NUMBER", 0);
        consultStatisticsByEmployeePresenter=new ConsultStatisticsByEmployeeController(this);

        consultStatisticsByEmployeePresenter.onConsultStatisticsForEmployee(actionNumber);
    }


public void initView(){
    barChart = findViewById(R.id.chart);
    reportPeriod =findViewById(R.id.stat_date);
     previous = findViewById(R.id.report_previous);
    next = findViewById(R.id.report_next);
    previous.setOnClickListener(this);
    next.setOnClickListener(this);

}

    @Override
    public void onStart(int firstDayNumber, int lastDayNameNumber, int mouthLength, int month, int year) {
        String [] days=getResources().getStringArray(R.array.days);
        String [] months=getResources().getStringArray(R.array.months);
        //From Monday 20 November 2022 to Friday 23 December 2022
        String from=getString(R.string.from);
        String to=getString(R.string.to);
        reportPeriod.setText(MessageFormat.format("{0} {1} 1 {2} {3} {4} {5} {6} {7} {3}",
                from, days[firstDayNumber - 1], months[month - 1], year,to, days[lastDayNameNumber - 1], mouthLength, months[month - 1], year));

    }

    @Override
    public void onMonthSelected(@NonNull Hashtable<Character, Float> statistics) {


        // calling method to get bar entries.
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        // if(statistics.containsKey('P'))


        Float valueP = statistics.get('P');
        if (valueP != null) {
            barEntriesArrayList.add(new BarEntry(1f, valueP));
        }
        Float valueR = statistics.get('R');
        if (valueR != null) {
            barEntriesArrayList.add(new BarEntry(2f, valueR));
        }
        Float valueA = statistics.get('A');
        if (valueA != null) {
            barEntriesArrayList.add(new BarEntry(3f, valueA));
        }


        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "");
        // adding color to our bar data set.
        Map<String, Integer> colorMap = new HashMap<>();
        colorMap.put("P", Color.GREEN);
        colorMap.put("R", Color.YELLOW);
        colorMap.put("A", Color.RED);


        barDataSet.setColors
                (colorMap.get("P"), colorMap.get("R"), colorMap.get("A"));

        //barDataSet.setColors(colorMap.values());
        barDataSet.setStackLabels(colorMap.keySet().toArray(new String[colorMap.size()]));


        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barDataSet.setDrawValues(true);

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);


      barChart.getDescription().setEnabled(false);
        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);
        barChart.setClickable(false);
        barChart.setEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.setActivated(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            barChart.setAllowClickWhenDisabled(false);
        }
        barChart.setFocusable(false);
        barChart.setFocusableInTouchMode(false);
        barChart.setHorizontalFadingEdgeEnabled(false);
        barChart.fitScreen();
        barChart.setHovered(false);
        barChart.setFitBars(true);
        barChart.setScrollContainer(false);
        barChart.setVerticalFadingEdgeEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.setScaleXEnabled(false);





        barChart.getDescription().setEnabled(false);
        Legend legend = barChart.getLegend();

        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.LINE);

    }

    @Override
    public void onReportNotAccessible(boolean nextMonthReport) {
        if(nextMonthReport)
            this.next.setClickable(false);
        else
            previous.setClickable(false);



    }

    @Override
    public void onReportAccessible(boolean nextMonthReport) {
        if(nextMonthReport)
            this.next.setClickable(true);
        else
            this.previous.setClickable(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(@NonNull View v) {
if (v.getId()==R.id.report_previous)

    consultStatisticsByEmployeePresenter.onPreviousMonth();

 else if (v.getId() == R.id.report_next)
    consultStatisticsByEmployeePresenter.onNextMonth();

    }
}