package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.SimpleEmployeeMenuController;
import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.settings.SimpleEmployeeSettings;
import uac.imsp.clockingapp.View.util.ISimpleEmployeeMenuView;

public class SimpleEmployeeMenu extends AppCompatActivity
        implements ISimpleEmployeeMenuView , View.OnClickListener {
    ISimpleEmployeeMenuController simpleEmployeeMenuPresenter;

int currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_employee_menu);
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.simple_employee_menu);
        initView();
        simpleEmployeeMenuPresenter =
                new SimpleEmployeeMenuController(this);

        currentUser=getIntent().getIntExtra("CURRENT_USER",0);
    }



    public boolean onCreateOptionsMenu(@NonNull android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.general_menu,menu);
        menu.removeItem(R.id.settings);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.logout:
                simpleEmployeeMenuPresenter.onExit();

                break;
            case android.R.id.home:
                finish();
                onBackPressed();
                break;
            default:
                break;



        }
        return super.onOptionsItemSelected(item);
    }


    public void initView(){
       //final Button password=findViewById(R.id.menu_password);
        //password.setOnClickListener(this);
        final Button clock=findViewById(R.id.menu_clock),
                report=findViewById(R.id.menu_presence_report),
                statistics=findViewById(R.id.menu_my_presence_statistics),
                settings=findViewById(R.id.menu_settings);
        clock.setOnClickListener(this);
        report.setOnClickListener(this);
        statistics.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onConsultatisticsMenuSuccessful() {
        startActivity((new Intent(this,ConsultStatisticsByEmployee.class)
        ).putExtra("ACTION_NUMBER",currentUser));


    }

    @Override
    public void onClocking() {

        startActivity(new Intent(this, ClockInOut.class));


    }

    @Override
    public void onSettings() {
        startActivity((new Intent(this, SimpleEmployeeSettings.class)

                .putExtra("CURRENT_USER",currentUser)));


    }

    @Override
    public void onConsultPresenceReport() {
        startActivity((new Intent(this,ConsultPresenceReport.class)
        ).putExtra("ACTION_NUMBER",currentUser));


    }

    @Override
    public void onExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.exit_confirmation_message))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    moveTaskToBack(true);
                    finishAffinity();


                });
        AlertDialog alert = builder.create();
        alert.setTitle(getString(R.string.exit_confirmation));
        alert.show();
    }

    @Override
    public void onClick(@NonNull View v) {
if(v.getId() == R.id.menu_clock)
    simpleEmployeeMenuPresenter.onClocking();
else if(v.getId()==R.id.menu_presence_report)
    simpleEmployeeMenuPresenter.onConsultPresentReport();
else if(v.getId()==R.id.menu_my_presence_statistics)
    simpleEmployeeMenuPresenter.onConsultatisticsMenu();
else if(v.getId()==R.id.menu_settings)
    simpleEmployeeMenuPresenter.onSettings();


    }
}