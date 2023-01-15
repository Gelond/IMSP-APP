        package uac.imsp.clockingapp.View.activity;

        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;

        import java.text.MessageFormat;
        import java.util.Objects;

        import entity.Day;
        import uac.imsp.clockingapp.Controller.control.ConsultPresenceReportController;
        import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
        import uac.imsp.clockingapp.R;
        import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReport extends AppCompatActivity
        implements IConsultPresenceReportView,
        View.OnClickListener {
        private TableLayout report;
        private TextView reportPeriod;
        private  Button previous,next;
        private int firstDayNumberInWeek;
 private String[] Report;
        IConsultPresenceReportController consultPresenceReportPresenter;


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            // calling the action bar
            ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.menu_presence_report);
                setContentView(R.layout.activity_consult_presence_report);

                consultPresenceReportPresenter=
                        new ConsultPresenceReportController
                                (this);
                initView();

        }
        public void initView(){
            int actionNumber = getIntent().
                    getIntExtra("ACTION_NUMBER", 0);

            previous = findViewById(R.id.report_previous);
                next = findViewById(R.id.report_next);
                previous.setOnClickListener(this);
                next.setOnClickListener(this);
                next.setOnClickListener(this);
                report =findViewById(R.id.report_table);
                reportPeriod =findViewById(R.id.report_date);
                consultPresenceReportPresenter.onConsultPresenceReport(actionNumber);
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

    /**
   This function takes as arguments :
   1-the report which is a table of strings that contains the presence state
   of employee during the concerned month
   2-the firstDayNumberInWeek which the number (from 1 to 7) of the the
   firstday in the current month (as a day of the first week)
   **/
        @Override
        public void onMonthSelected(String[] report, int firstDayNumberInWeek) {

                Report=report;
                this.firstDayNumberInWeek=firstDayNumberInWeek;
                setReportVisible();
        }

        public void setReportVisible(){
                TableRow tableRow;
                boolean allBrowsed=false;
                Day day=new Day();
                TextView textView;
                int i,j,c=-1;
                for(i=1;i<=6;i++)
                {


                        if(allBrowsed)
                        {
                                for(c=i;c<=6;c++) {
                                        tableRow = (TableRow) report.getChildAt(c);
                                        report.removeView(tableRow);
                                }
                                break;
                        }

                        tableRow = (TableRow) report.getChildAt(i);
                        for(j=1;j<=7;j++) {
                                if (i == 1 && j < firstDayNumberInWeek)
                                        continue;

                                c++;
                                //all days are browsed
                                if(c==Report.length) {
                                        allBrowsed=true;
                                        break;
                                }

                                textView=((TextView) (tableRow.getChildAt(j - 1)));
                                textView.setText(String.valueOf(c+1));
                                //mark the current date
                                if(c+1==day.getDayOfMonth())
                                        textView.setTextColor(Color.rgb(15,99,200));

                                if(Objects.equals(Report[c], "Présent"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.GREEN);
                                else   if(Objects.equals(Report[c], "Absent"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.RED);
                                else if(Objects.equals(Report[c], "Retard"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.YELLOW);
                                else if(Objects.equals(Report[c], "Hors service"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.BLUE);
                        }

                }


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
        public void onClick(@NonNull View v) {
                if(v.getId()==R.id.report_previous)
                        consultPresenceReportPresenter.onPreviousMonth();
                else if (v.getId()==R.id.report_next)
                        consultPresenceReportPresenter.onNextMonth();
        }
}