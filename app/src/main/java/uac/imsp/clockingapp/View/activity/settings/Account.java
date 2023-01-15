package uac.imsp.clockingapp.View.activity.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import uac.imsp.clockingapp.Controller.control.ChangePasswordController;
import uac.imsp.clockingapp.Controller.util.IChangePasswordController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IAccountView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Account extends AppCompatActivity implements View.OnClickListener,
		IAccountView {
	ConstraintLayout passwordLayout;
	private IChangePasswordController changePasswordPresenter;
	EditText oldPassword,newPassword,newPasswordConfirm;
	TextView username;
	Button changePassword,update;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.my_account);
		initView();
		changePasswordPresenter=new ChangePasswordController(this) ;

		changePasswordPresenter.onStart(getIntent().getIntExtra("CURRENT_USER",1));

	}

	public void initView(){
		 changePassword=findViewById(R.id.change_password);
		 update=findViewById(R.id.change_password_button);
		 update.setOnClickListener(this);
		 username=findViewById(R.id.username);
		  passwordLayout =findViewById(R.id.password_layout);
		changePassword.setOnClickListener(this);
	}

	@Override
	public void onClick(@NonNull View v) {
		if(v.getId()==R.id.change_password) {
			username.setVisibility(View.GONE);
			changePassword.setVisibility(View.GONE);
			passwordLayout.setVisibility(View.VISIBLE);
			newPassword = findViewById(R.id.change_password_new_password);
			oldPassword = findViewById(R.id.change_password_old_password);
			newPasswordConfirm = findViewById(R.id.change_password_new_password_confirm);
		}
		else if(v.getId()==R.id.change_password_button) {

			changePasswordPresenter.onSubmit(oldPassword.getText().toString().trim(),
					newPassword.getText().toString().trim(),
					newPasswordConfirm.getText().toString().trim()
			);
		}

	}

	@Override
	public void onSuccess() {
		String message=getString(R.string.password_changed_successfully);
		new ToastMessage(this,message);


	}

	@Override
	public void onWrongPassword() {
		String message=getString(R.string.password_invalid);
		new ToastMessage(this,message);

	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart(String username) {
		this.username.setText(username);

	}
}