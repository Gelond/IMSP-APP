package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.Controller.control.StartScreenController;
import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.LocalHelper;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IStartScreenView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class StartScreen extends AppCompatActivity
implements View.OnClickListener  , IStartScreenView {

    private Intent intent;
    private IStartScreenController startScreenPresenter;
    int currentVersionCode,savedVersionCode;
    final String PREFS_NAME="MyPrefsFile",
            PREF_VERSION_CODE_KEY="version_code";
    final int DOESNT_EXIST=-1;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String lang;

  boolean dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);


        dark=preferences.getBoolean("dark",false);



        lang=preferences.getString("lang",Locale.getDefault().getLanguage());
        LocalHelper.changeAppLanguage(this,lang);





        startScreenPresenter = new StartScreenController(this);


        //initializing

        editor = preferences.edit();
        savedVersionCode = preferences.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
        // Get current version code
        currentVersionCode = BuildConfig.VERSION_CODE;
        startScreenPresenter.onLoad(savedVersionCode, currentVersionCode);


        //update the shared preferences with the current version code
        editor.putInt(PREF_VERSION_CODE_KEY, currentVersionCode);
        editor.apply();

        startScreenPresenter = new StartScreenController(this);
       // initView();


    }



    public void initView(){

        Button login = findViewById(R.id.start_screen_login_button);
        Button clocking = findViewById(R.id.start_screen_clock_button);
        login.setOnClickListener(this);
        clocking.setOnClickListener(this);









    }
    @Override
    public void onClick(@NonNull View v) {
        if(v.getId()==R.id.start_screen_login_button)
            startScreenPresenter.onLogin();
        else if(v.getId()==R.id.start_screen_clock_button)
            startScreenPresenter.onClocking();


    }

    @Override
    public void onLogin() {
        intent=new Intent(StartScreen.this,Login.class);
        startActivity(intent);


    }

    @Override
    public void onClocking() {
        intent=new Intent(StartScreen.this,ClockInOut.class);
        startActivity(intent);


    }

    @Override
    public void onFirstRun() {

        Intent intent=new Intent(StartScreen.this, SetUp.class);
        StartScreen.this.finish();
        startActivity(intent);

    }

    @Override
    public void onUpgrade() {

    }

    @Override
    public void onNormalRun() {
        setContentView(R.layout.activity_start_screen);
        initView();
    }

    @Override
    public void onSetUp() {
        Intent intent=new Intent(StartScreen.this, SetUp.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onService() {
        Intent intent=new Intent(StartScreen.this, Services.class);
        finish();
        startActivity(intent);

    }

    @Override
    public void onAccount() {
        Intent intent=new Intent(StartScreen.this, ShowAdminAccount.class);
        finish();
        startActivity(intent);

    }

    @Override
    public void onDowngrade() {
new ToastMessage(this,"Downgrade impossible");
    }

}