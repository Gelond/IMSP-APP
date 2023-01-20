package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class ReportProblem extends AppCompatActivity {
	boolean dark;
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

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
		setContentView(R.layout.activity_report_problem);
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.report_problem);
	}
}