package uac.imsp.clockingapp.View.activity.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Overview extends AppCompatActivity implements View.OnClickListener {
	Button cpyOverview;
	TextView appVersionName,sdkVersion,osVersion,deviceName,deviceManufacturer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		initView();
	}
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	public void initView(){
		cpyOverview=findViewById(R.id.overview_copy);
		cpyOverview.setOnClickListener(this);

		appVersionName=findViewById(R.id.overview_app_version);
		appVersionName.setText(BuildConfig.VERSION_NAME);

		deviceName=findViewById(R.id.overview_device_name);
		deviceName.setText(String.valueOf(Build.MODEL));
		deviceManufacturer=findViewById(R.id.overview_device_manufacturer);
		deviceManufacturer.setText(String.valueOf(Build.MANUFACTURER));

		sdkVersion=findViewById(R.id.overview_sdk_version);
		sdkVersion.setText(String.valueOf(Build.VERSION.SDK_INT));

		osVersion=findViewById(R.id.overview_version_name);
		osVersion.setText(String.valueOf(Build.VERSION.RELEASE));


	}

	@Override
	public void onClick(@NonNull View v) {
		String overview;
		overview =getString(R.string.app_version) +":\n-"+
				appVersionName.getText().toString()+":\n"+
		getString(R.string.device_name)+":\n" +
				"-"+appVersionName.getText().toString()+"\n"+
				getString(R.string.device_manufacturer)+"\n" +
				"-"+deviceManufacturer.getText().toString()+"\n"+
		getString(R.string.version_name)+":\n"+
		"-"+osVersion.getText().toString()+"\n"+
		getString(R.string.sdk_version)+":\n"+
		"-"+sdkVersion.getText().toString();

		ClipboardManager clipboard;
		ClipData clip;
if(v.getId()==R.id.overview_copy)
{
	clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	 clip = ClipData.newPlainText("overview", overview);
	clipboard.setPrimaryClip(clip);
	new ToastMessage(this,getString(R.string.copied));
}

	}
}