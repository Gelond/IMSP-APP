package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Upgrade extends AppCompatActivity {

	boolean dark;
	private void retrieveSharedPreferences() {
		String PREFS_NAME="MyPrefsFile";
		SharedPreferences preferences= getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		dark=preferences.getBoolean("dark",false);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		retrieveSharedPreferences();
		if(dark)
			setTheme(R.style.DarkTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrage);
		new ToastMessage(this,"Mise à jour efféctuée avec succès");
	}
}