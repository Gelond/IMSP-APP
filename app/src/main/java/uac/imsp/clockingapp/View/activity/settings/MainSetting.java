
package uac.imsp.clockingapp.View.activity.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.Controller.control.settings.MainSettingsController;
import uac.imsp.clockingapp.Controller.util.settings.IMainSettingsController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.Services;
import uac.imsp.clockingapp.View.activity.SimpleEmployeeMenu;
import uac.imsp.clockingapp.View.activity.settings.others.Clocking;
import uac.imsp.clockingapp.View.activity.settings.others.DarkMode;
import uac.imsp.clockingapp.View.activity.settings.others.Help;
import uac.imsp.clockingapp.View.activity.settings.others.Languages;
import uac.imsp.clockingapp.View.activity.settings.others.ReportProblem;
import uac.imsp.clockingapp.View.util.settings.IMainSettingsView;

public class MainSetting extends AppCompatActivity implements View.OnClickListener, IMainSettingsView {
TextView share,shareViaQR,overview,appVersion,clearCache,cacheSize,name,clock,dark,lang,problem,help,
		email,desc,accountSettings,service,logout,simple;
	EditText input;
Intent intent;
ConstraintLayout nameLayout;
	ConstraintLayout emailLayout;
	ConstraintLayout descLayout;
	long cacheSizeValue;
String dialogInput;

SharedPreferences preferences;
	final String PREFS_NAME="MyPrefsFile";
	SharedPreferences.Editor editor;
	String Mail,Name,Desc;
	String prefs;

IMainSettingsController mainSettingsPresenter;
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
		setContentView(R.layout.activity_main_setting);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.settings));
		mainSettingsPresenter=new MainSettingsController(this);
		initView();
		retrieveSharedPreferences();
	}
	public void initView(){
		service=findViewById(R.id.setting_services);
		service.setOnClickListener(this);
		share=findViewById(R.id.setting_share);
		shareViaQR=findViewById(R.id.setting_share_with_qr);
		overview=findViewById(R.id.setting_overview);
		appVersion=findViewById(R.id.setting_version);
		clearCache=findViewById(R.id.setting_clear_cache);


		cacheSize=findViewById(R.id.setting_cache_size);
		nameLayout=findViewById(R.id.setting_name_layout);
		simple=findViewById(R.id.switch_to_simple);
		simple.setOnClickListener(this);
		logout=findViewById(R.id.logout);
		logout.setOnClickListener(this);
		nameLayout.setOnClickListener(this);
		name=findViewById(R.id.setting_name);
		email=findViewById(R.id.setting_mail);
		desc=findViewById(R.id.setting_description);
		clock=findViewById(R.id.setting_attendance);
		dark=findViewById(R.id.setting_dark);
		lang=findViewById(R.id.setting_languages);
		problem=findViewById(R.id.setting_problem);
		help=findViewById(R.id.setting_help);
		accountSettings=findViewById(R.id.setting_account);
		clock.setOnClickListener(this);
		dark.setOnClickListener(this);
		lang.setOnClickListener(this);
		problem.setOnClickListener(this);
		help.setOnClickListener(this);
		accountSettings.setOnClickListener(this);
		emailLayout=findViewById(R.id.setting_mail_layout);
		emailLayout.setOnClickListener(this);
		descLayout=findViewById(R.id.setting_description_layout);
		descLayout.setOnClickListener(this);
		clearCache.setOnClickListener(this);

		cacheSize();
		cacheSize.setText(String.format("%s Mo", cacheSizeValue));
		appVersion.setText(BuildConfig.VERSION_NAME);
		share.setOnClickListener(this);
		shareViaQR.setOnClickListener(this);
		overview.setOnClickListener(this);



	}



	@Override
	public void onClick(@NonNull View v) {

		if(v.getId()==R.id.setting_services)
			mainSettingsPresenter.onService();
        else if(v.getId()==R.id.setting_share)
	        mainSettingsPresenter.onShareApp();
        else if(v.getId()==R.id.setting_share_with_qr)
	        mainSettingsPresenter.onShareAppViaQRCode();
        else if(v.getId()==R.id.setting_overview)
			mainSettingsPresenter.onOverview();
        else if (v.getId()==R.id.setting_clear_cache) {
			if(cacheSizeValue!=0)
			   mainSettingsPresenter.onClearAppCache();
		}
        else if (v.getId()==R.id.setting_name_layout)
	        mainSettingsPresenter.onName();
        else if(v.getId()==R.id.setting_mail_layout)
	        mainSettingsPresenter.onEmail();
        else if(v.getId()==R.id.setting_description_layout)
	        mainSettingsPresenter.onDescription();
        else if(v.getId()==R.id.setting_account)
            mainSettingsPresenter.onAccount();
        else if (v.getId()==R.id.setting_attendance) {
	        mainSettingsPresenter.onClocking();
}
        else if(v.getId()==R.id.setting_dark)
	mainSettingsPresenter.onDarkMode();
        else if(v.getId()==R.id.setting_languages)
	mainSettingsPresenter.onLanguague();
		else if(v.getId()==R.id.setting_help)
	mainSettingsPresenter.onHelp();
		else if(v.getId()==R.id.setting_problem)
	mainSettingsPresenter.onReportProblem();
		else if(v.getId()==R.id.logout)
	mainSettingsPresenter.onLogout();
		else if(v.getId()==R.id.switch_to_simple)
	mainSettingsPresenter.onSwitch();

}


	@Override
	public void onShareApp() {
		intent=new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.download_link)+
				"https://drive.google.com/file/d/1t_F2tkCxMHNlNZuiseF6ozprgqdUvCTd/view");

		startActivity(Intent.createChooser(intent, getString(R.string.share_via)));

	}

	public void retrieveSharedPreferences(){
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		Name=preferences.getString("entrepriseName","");
		Mail=preferences.getString("entrepriseEmail","");
		Desc=preferences.getString("entrepriseDescription","");
	name.setText(Name);
	email.setText(Mail);
	desc.setText(Desc);

	}

	@Override
	public void onShareAppViaQRCode() {
		intent=new Intent(this,ShareAppViaQRCode.class);
		startActivity(intent);

	}

	@Override
	public void onAppcacheCleared() {
cacheSize.setText(String.format("%s Mo", cacheSizeValue));
	}

	@Override
	public void onName(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg,0 );

	}

	@Override
	public void onEmail(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg,1 );

	}

	@Override
	public void onDescription(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg,2 );

	}


	@Override
	public void onOverview() {
		intent=new Intent(MainSetting.this,Overview.class);
		startActivity(intent);
	}

	@Override
	public void onAccount() {
intent=new Intent(MainSetting.this,ManageUsername.class);
startActivity(intent);
	}

	@Override
	public void onService() {
intent=new Intent(this, Services.class);
startActivity(intent);
	}


	@Override
	public void onClock() {
		intent=new Intent(MainSetting.this, Clocking.class);
		startActivity(intent);

	}

	@Override
	public void onDark() {
		intent=new Intent(MainSetting.this, DarkMode.class);
		startActivity(intent);

	}

	@Override
	public void onLanguage() {
		intent=new Intent(MainSetting.this, Languages.class);
		startActivity(intent);

	}

	@Override
	public void onProblem() {
		intent=new Intent(MainSetting.this, ReportProblem.class);
		startActivity(intent);

	}

	@Override
	public void onHelp() {
		intent=new Intent(MainSetting.this, Help.class);
		startActivity(intent);

	}

	@Override
	public void onLogout() {
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
	public void onSwitch() {
startActivity((new Intent(this, SimpleEmployeeMenu.class)
		).putExtra("CURRENT_USER",getIntent()
		.getIntExtra("CURRENT_USER",0))
);
	}

	public void cacheSize(){
		cacheSizeValue=0;
		File[] files = getCacheDir().listFiles();
		assert files != null;
		for (File f:files)
			cacheSizeValue+= f.length();
		cacheSizeValue/=(1024*1024);

	}
	public void  inputDialog(String title,String msg, String pos, String neg,int value ){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		input = new EditText(MainSetting.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		input.setLayoutParams(lp);
		if(value==0)
			prefs=Name;
		else if(value==1)
			prefs=Mail;
		else if(value==2)
			prefs=Desc;
		input.setText(prefs);
		input.setSelectAllOnFocus(true);
		builder.setView(input);
		builder.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(pos, (dialog, which) -> {

					dialogInput= input.getText().toString().trim();
					prefs = dialogInput;
					editor=preferences.edit();
					if(value==0) {

						name.setText(dialogInput);
						editor.putString("entrepriseName",dialogInput);


					}
					else if(value==1) {
						email.setText(dialogInput);
						editor.putString("entrepriseEmail",dialogInput);
					}
					else if(value==2) {

						desc.setText(dialogInput);
						editor.putString("entrepriseDescription",dialogInput);
					}
					editor.apply();
					retrieveSharedPreferences();
				})
				.setNegativeButton(neg,(dialog,which)->{

				});
		AlertDialog alert = builder.create();
		alert.setTitle(title);
		alert.show();



	}
}