package uac.imsp.clockingapp.View.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.ConsultStatisticsByServiceController;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByServiceController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByServiceView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ConsultStatisticsByService extends AppCompatActivity
implements IConsultStatisticsByServiceView, AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private IConsultStatisticsByServiceController consultStatisticsByServicePresenter;
    PieChart pieChart;
    private TextView reportPeriod;
    private Hashtable<String,Integer> statistics;
    private Button previous,next;
    private int Status=0;
    //private String selectedStatus;
    Spinner spinnerStatus;
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consult_statistic_by_service);
            // calling the action bar
            ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.menu_statistics_by_service);
           initView();
            consultStatisticsByServicePresenter=new
                    ConsultStatisticsByServiceController(this);
            consultStatisticsByServicePresenter.onConsultStatisticsByService();



        }
        public void initView(){
            pieChart = findViewById(R.id.pieChart_view);
            initPieChart();
            previous=findViewById(R.id.report_previous);
            next=findViewById(R.id.report_next);
            previous.setOnClickListener(this);
            next.setOnClickListener(this);
             spinnerStatus = findViewById(R.id.stat__status);
            reportPeriod=findViewById(R.id.report_date);


       String[]     status=getResources().getStringArray(R.array.status);
            ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
            dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStatus.setAdapter(dataAdapterR);
            spinnerStatus.setOnItemSelectedListener(this);
            pieChart.setPaddingRelative(1,1,1,1);

        }

    private void showPieChart(){


        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //initializing data
        HashMap<String, Integer> resultMap = new HashMap<>(statistics);
        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();


       // pieDataSet.setColors(Color.parseColor("#F44336"),
               colors.add(Color.parseColor("#3F51B5"));
               colors.add(Color.parseColor("#8BC34A"));
               colors.add( Color.parseColor("#9C27B0"));
               colors.add(Color.parseColor("#FF5722"));
               colors.add( Color.parseColor("#00BCD4"));
               colors.add(Color.parseColor("#03A9F4"));
               colors.add(Color.parseColor("#4CAF50"));
               colors.add(Color.parseColor("#FF9800"));
               colors.add(Color.parseColor("#E91E63"));

        colors.add(Color.parseColor("#2196F3"));
        colors.add( Color.parseColor("#9E9E9E"));
        colors.add(Color.parseColor("#FFEB3B"));
        colors.add(Color.parseColor("#CDDC39"));
        colors.add(Color.parseColor("#795548"));

        //input data and fit data into pie chart entry
        for(String service: resultMap.keySet()){
            pieEntries.add(new PieEntry(Objects.requireNonNull
                    (resultMap.get(service)), service));

        }
        findViewById(R.id.stat__status).setMinimumHeight(100);
       // findViewById(R.id.stat__status).setminimumHeight(100);

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");

        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.getLegend().setWordWrapEnabled(true);

       // pieChart.setCenterText(label);
       // pieChart.getDescription().setText(label);

    }
    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(false);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(true);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"));

    }


    @Override
    public void onNoServiceFound() {
        String message=getString(R.string.no_service_available);
            new ToastMessage(this,message);
            next.setVisibility(View.GONE);
            previous.setVisibility(View.GONE);
            spinnerStatus.setVisibility(View.GONE);
    }

    @Override
    public void onServiceFound(Hashtable<String, Integer> rowSet) {
        statistics=rowSet;
        showPieChart();

    }

    @Override
    public void onStart(int firstDayNumber, int lastDayNumber, int mouthLength, int month, int year) {
        String [] days=getResources().getStringArray(R.array.days);
        String [] months=getResources().getStringArray(R.array.months);
        //From Monday 20 November 2022 to Friday 23 December 2022
        String from=getString(R.string.from);
        String to=getString(R.string.to);
        reportPeriod.setText(MessageFormat.format("{0} {1} 1 {2} {3} {4} {5} {6} {7} {3}",
                from, days[firstDayNumber - 1], months[month - 1], year,to, days[lastDayNumber - 1], mouthLength, months[month - 1], year));

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
    public void onReportStatusChanged(int status) {

        Hashtable<String, Integer> rowSet=consultStatisticsByServicePresenter.
                onReportStatusChanged(status);
            onServiceFound(rowSet);

    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId()==R.id.report_next)

            consultStatisticsByServicePresenter.onPreviousMonth();

        else if (v.getId() == R.id.report_next)
            consultStatisticsByServicePresenter.onNextMonth();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(Status!=position) {
            onReportStatusChanged(position);
            Status=position;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
