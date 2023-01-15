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

import uac.imsp.clockingapp.Controller.control.GeneralMenuController;
import uac.imsp.clockingapp.Controller.util.IGeneralMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.settings.MainSetting;
import uac.imsp.clockingapp.View.util.IGeneralMenuView;

public class GeneralMenu extends AppCompatActivity implements View.OnClickListener,
        IGeneralMenuView {

    IGeneralMenuController menuPresenter;
    private int currentUser;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        menuPresenter.onExit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_menu);
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.menu);
       initView();
       menuPresenter= new GeneralMenuController(this) ;
       currentUser=getIntent().getIntExtra("CURRENT_USER",0);

    }
        @Override


    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.general_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {


            case R.id.settings :
                startActivity((new Intent(this, MainSetting.class))
                        .putExtra(
                        "CURRENT_USER",currentUser
                ));

                break;
            case R.id.logout:

                menuPresenter.onExit();
                break;
            case  android.R.id.home:
                finish();
                startActivity(new Intent(this, StartScreen.class));

            break;
            default:
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(@NonNull View v) {
        boolean presenter;
         if(v.getId()==R.id.menu_register)
         {

            presenter= menuPresenter.onRegisterEmployeeMenu(currentUser);
           if(!presenter)//there is no service
           {
               startActivity(new Intent(GeneralMenu.this,NoServiceAvailable.class).
                       putExtra("CURRENT_USER",currentUser));


           }
         }
        else if(v.getId()==R.id.menu_clock)
            menuPresenter.onClocking();
        else if (v.getId()==R.id.menu_search)
            menuPresenter.onSearchEmployeeMenu(currentUser);

        else if(v.getId()==R.id.menu_statistics_by_servie)
            menuPresenter.onConsultatisticsMenu(currentUser);




    }


    @Override
    public void onSearchEmployeeMenuSuccessfull() {

        startActivity((new Intent(this, SearchEmployee.class).putExtra("CURRENT_USER",currentUser)));

    }

    @Override
    public void onUpdateEmployeeMenuSuccessfull() {

        startActivity( new Intent(this, UpdateEmployee.class));

    }

    @Override
    public void onDeleteEmployeeMenuSucessfull() {

        startActivity(new Intent(this, DeleteEmployee.class));

    }

    @Override
    public void onRegisterEmployeeMenuSuccessful() {
        startActivity(new Intent(this, RegisterEmployee.class));

    }

    @Override
    public void onConsultatisticsMenuSuccessful() {
        startActivity(new Intent(this, ConsultStatisticsByService.class));

    }

    @Override
    public void onClocking() {


        startActivity(new Intent(this, ClockInOut.class));

    }

    @Override
    public void onConsultPresenceReport() {

        startActivity(new Intent(this,ConsultPresenceReport.class));

    }

    @Override
    public void onExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.exit_confirmation_message))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    moveTaskToBack(true);
                   // android.os.Process.killProcess(android.os.Process.myPid());
                   // System.exit(0);
                    finishAffinity();


                });
        AlertDialog alert = builder.create();
        alert.setTitle(getString(R.string.exit_confirmation));
        alert.show();
    }


    public void  initView(){
        Button register = findViewById(R.id.menu_register);
        Button clock = findViewById(R.id.menu_clock);
        Button search = findViewById(R.id.menu_search);

        Button statisticsByService = findViewById(R.id.menu_statistics_by_servie);



        register.setOnClickListener(this);
        clock.setOnClickListener(this);
        search.setOnClickListener(this);
        search.setOnClickListener(this);

        statisticsByService.setOnClickListener(this);

    }
}