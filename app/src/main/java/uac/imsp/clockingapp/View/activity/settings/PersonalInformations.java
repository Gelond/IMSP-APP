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

import uac.imsp.clockingapp.Controller.control.settings.PersonalInformationsController;
import uac.imsp.clockingapp.Controller.util.IPersonalInformationsController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;
import uac.imsp.clockingapp.View.util.settings.IPersonalInformationsView;

public class PersonalInformations extends AppCompatActivity
		implements IPersonalInformationsView, View.OnClickListener {

TextView lastname,firstname;
EditText email;
Button update, cancel;
int number;
IPersonalInformationsController personalInformationsPresenter;
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.personal_informations);
		setContentView(R.layout.activity_personal_informations);
		initView();
		personalInformationsPresenter=new PersonalInformationsController(this);
		number=getIntent().getIntExtra("CURRENT_USER",0);
		personalInformationsPresenter.onStart(number);

	}

	public void initView(){
		firstname=findViewById(R.id.firstname);
		lastname=findViewById(R.id.lastname);
		email=findViewById(R.id.mail);
		update=findViewById(R.id.update);
		cancel =findViewById(R.id.cancel);
		update.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}


	@Override
	public void onClick(@NonNull View v) {
		if(v.getId()==R.id.update)
			personalInformationsPresenter.onUpdate(email.getText().toString().trim());
       else if(v.getId()==R.id.cancel)
		   personalInformationsPresenter.onStart(number);
	}

	@Override
	public void onError(int errorNumber) {
		switch (errorNumber) {
			case 0:
			new ToastMessage(this,getString(R.string.mail_required));
					break;
			case 1:
				new ToastMessage(this,getString(R.string.mail_invalid));
				break;

			default:
				break;

		}

	}

	@Override
	public void onUpdateSuccessfull() {
		new ToastMessage(this,getString(R.string.succces));
		personalInformationsPresenter.onStart(number);

	}

	@Override
	public void onStart(String firstname, String lastname, String mailAddress) {
		this.firstname.setText(firstname);
		this.lastname.setText(lastname);
		this.email.setText(mailAddress);

	}
}