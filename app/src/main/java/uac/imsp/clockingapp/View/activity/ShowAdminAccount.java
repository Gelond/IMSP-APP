package uac.imsp.clockingapp.View.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.ShowAdminAcountController;
import uac.imsp.clockingapp.Controller.util.IShowAdminAcountController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IShowAdminAcountView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ShowAdminAccount extends AppCompatActivity
		implements IShowAdminAcountView , View.OnClickListener {
	 TextView username, password;
	IShowAdminAcountController showAdminAcountPresenter;
	SharedPreferences preferences;
	final String PREFS_NAME="MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
		setContentView(R.layout.activity_show_admin_account);
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.acccount_informations);
		initView();
		showAdminAcountPresenter=new ShowAdminAcountController(this);
		showAdminAcountPresenter.onLoad();
	}

	@Override
	public void onStart(String username, String password) {
		this.username.setText(username);
		this.password.setText(password);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId()==android.R.id.home)
		{
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCopyAccount() {
		String account="Username: "+username.getText().toString()+"\nPassword: " +
				password.getText().toString();
		ClipboardManager clipboard;
		ClipData clip;
			clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clip = ClipData.newPlainText("admin", account);
			clipboard.setPrimaryClip(clip);
			new ToastMessage(this,getString(R.string.copied));

	}

	@Override
	public void onNext() {

		startActivity(new Intent(this,StartScreen.class));

	}
	public void initView(){
		final Button copy=findViewById(R.id.copy),
		next=findViewById(R.id.next);
		copy.setOnClickListener(this);
		 username=findViewById(R.id.username);
		password=findViewById(R.id.password);
		next.setOnClickListener(this);
	}

	@Override
	public void onClick(@NonNull View v) {
if(v.getId()==R.id.next)
	showAdminAcountPresenter.onNext();
else if(v.getId()==R.id.copy)
	showAdminAcountPresenter.onCopyAccount();

	}
}