package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

import uac.imsp.clockingapp.Controller.control.settings.others.DarkModeController;
import uac.imsp.clockingapp.Controller.util.settings.others.IDarkModeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.others.IDarkModeView;

public class DarkMode extends AppCompatActivity
		implements CompoundButton.OnCheckedChangeListener, IDarkModeView {
	SwitchMaterial darkMode;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Boolean DarkMode;
	IDarkModeController darkModePresenter;
	String 	PREFS_NAME="MyPrefsFile";
	boolean isChecked;
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			//finish();
			//onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.DarkTheme);
		super.onCreate(savedInstanceState);
		darkModePresenter=new DarkModeController(this);
		setContentView(R.layout.activity_dark_mode);
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.dark);
		initView();
		retrieveSharedPreferences();
		darkMode.setChecked(DarkMode);

	}
	public void initView(){
darkMode=findViewById(R.id.dark_mode);
//darkMode.setOnCheckedChangeListener(this);
	}
	public void retrieveSharedPreferences(){
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor=preferences.edit();

       DarkMode=preferences.getBoolean("dark",true);
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
	}

	@Override
	public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
if(buttonView.getId()==R.id.dark_mode)
{
	this.isChecked=isChecked;
	darkModePresenter.onDarkMode();
}


	}


	@Override
	public void onDarkMode() {
		editor.putBoolean("dark",isChecked);
		if(isChecked)
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		else
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		editor.apply();
		recreate();
		//restartApp();


	}
	/*public void restartApp() {
		PackageManager pm = getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(getPackageName());
		finishAffinity(); // Finishes all activities.
		startActivity(intent);    // Start the launch activity
		overridePendingTransition(0, 0);
	}*/
}
