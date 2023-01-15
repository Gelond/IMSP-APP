package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.control.ServicesController;
import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IServicesView;
import uac.imsp.clockingapp.View.util.ServiceListViewAdapter;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Services extends AppCompatActivity implements IServicesView,
		View.OnClickListener, AdapterView.OnItemLongClickListener/*,
		AdapterView.OnItemClickListener, TextWatcher */,
		View.OnFocusChangeListener {
	private ListView list;
	ServiceListViewAdapter adapter;
	ServicesController servicesPresenter;
	ArrayList<ServiceResuslt> arrayList;
	 Button addService;
	 EditText editService;
	//EditText serviceListViewItemEditText;
	//int position;
	//View view;
	//ViewGroup parent;
	/*String oldService,newService;
	Hashtable<Integer, String> hashtable=new Hashtable<>();*/

	SharedPreferences.Editor editor;
	SharedPreferences preferences;

	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final String PREFS_NAME="MyPrefsFile";
		preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor=preferences.edit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.service_management);
		initView();
		servicesPresenter=new ServicesController(this);
		servicesPresenter.onStart();

	}

	@Override
	public void onNoServiceFound() {

		list.setVisibility(View.GONE);



	}

	@Override
	public void onServiceFound(ArrayList<ServiceResuslt> list) {
		adapter = new ServiceListViewAdapter(this, list);
		this.list.setAdapter(adapter);
		this.list.setVisibility(View.VISIBLE);
		arrayList=list;


	}

	@SuppressLint("ClickableViewAccessibility")
	public void initView(){
		final Button next=findViewById(R.id.next);

		//final Button cancel = findViewById(R.id.cancel);
	//final 	Button apply = findViewById(R.id.apply);
	if(preferences.getString("nextStep","none").equals("service"))
	{
		ConstraintLayout constraintLayout = findViewById(R.id.layout);
		ConstraintSet constraintSet = new ConstraintSet();
		constraintSet.clone(constraintLayout);
		constraintSet.centerHorizontally(R.id.next, ConstraintSet.PARENT_ID);
		constraintSet.applyTo(constraintLayout);
		next.setVisibility(View.VISIBLE);
		next.setOnClickListener(this);
	}


		addService=findViewById(R.id.add_service);
		editService=findViewById(R.id.edit_service);

		editService.setCursorVisible(false);

		addService.setOnClickListener(this);
	addService.setSelectAllOnFocus(true);
		//cancel.setOnClickListener(this);
		//apply.setOnClickListener(this);
		list=findViewById(R.id.service_list);
		list.setVisibility(View.GONE);
		list.setOnItemLongClickListener(this);
		//list.setOnItemClickListener(this);
		editService.setOnClickListener(this);

		/*list.setOnTouchListener((v, event) -> {
			int action = event.getAction();
			if (action == MotionEvent.ACTION_UP) {
				new ToastMessage(getApplicationContext(),"not consummed");
				// The event is not consumed, it will reach the OnItemClickListener
			} else if (action == MotionEvent.ACTION_CANCEL) {
				// The event is consumed
				new ToastMessage(getApplicationContext(),"consumed");
			}
			return false;
		});*/

	}


	@Override
	public void askConfirmDelete() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.confirm_delete_service))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->
						servicesPresenter.onDeleteConfirm(true))
				.setNegativeButton(getString(R.string.no), (dialog, which) ->{

						});


		AlertDialog alert = builder.create();
		alert.setTitle(getString(R.string.delete_confirmation));
		alert.show();

	}

/*	@Override
	public void onUpdateError(int errorNumber, int serviceIndex) {
		int position=	getTheMatchingPosition(serviceIndex);
		switch (errorNumber)
		{

			case 0:
				new ToastMessage(this,
						getString(R.string.service_invalid));


			adapter.getServiceNameEditText(position,view,parent).
					setBackgroundColor(Color.RED);

			break;

			case 1:
				new ToastMessage(this,getString(R.string.service_already_exists));
				adapter.getServiceNameEditText(position,view,parent).
						setBackgroundColor(Color.YELLOW);
				break;
			default:
				break;

		}

	}*/

	@Override
	public void onDeleteSucessful() {

		new ToastMessage(this,getString(R.string.service_deleted));
		servicesPresenter.onStart();

	}

	@Override
	public void onDeleteError(int errorNumber) {
		switch (errorNumber){
			case 0:
			new ToastMessage(this,getString(R.string.no_such_service));
				break;
			case 1:
				new ToastMessage(this,getString(R.string.delete_not_possible));
				break;
			default:
				break;
		}

	}


	/*@Override
	public void onServiceEdited() {
		serviceListViewItemEditText.setBackgroundColor(Color.BLUE);
		serviceListViewItemEditText.setText(newService);
	hashtable.put(adapter.getItem(position).getId(),newService);

	}*/

	@Override
	public void onAddServiceError(int errorNumber) {

switch (errorNumber){
	case 0:
		new ToastMessage(this,getString(R.string.service_invalid));
		editService.setOnFocusChangeListener(this);
		editService.setBackgroundColor(Color.RED);



		break;
	case 1:
		new ToastMessage(this,getString(R.string.service_already_exists));
		editService.setOnFocusChangeListener(this);
		editService.setBackgroundColor(Color.YELLOW);

		break;
	default:
		break;
}
	}

	@Override
	public void onServiceAdded() {
		new ToastMessage(this,getString(R.string.notify_service_added));
		editService.setText("");
		servicesPresenter.onStart();
	}

	/*@Override
	public void askConfirmCancel() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.cancel_confirmation_message))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->
						servicesPresenter.onStart())
				.setNegativeButton(getString(R.string.no), (dialog, which) -> {
				});
		AlertDialog alert = builder.create();
		alert.setTitle(getString(R.string.cancel_confirmation_title));
		alert.show();

	}

	@Override
	public void askConfirmUpdate() {


		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirm_update_service)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->{
						servicesPresenter.onUpdateConfirmed();
						hashtable=new Hashtable<>();
				})
				.setNegativeButton(getString(R.string.no), (dialog, which) -> {
					servicesPresenter.onStart();
					hashtable=new Hashtable<>();
				});
		AlertDialog alert = builder.create();
		alert.setTitle(R.string.confirm_update_service_title);
		alert.show();


	}

	@Override
	public void onNothingUpdated() {
new ToastMessage(this,getString(R.string.no_service_updated));

	}*/

	@Override
	public void onClick(@NonNull View v) {

		if(v.getId()==R.id.add_service)
			servicesPresenter.onAddService(editService.getText().toString().trim());
		else if(v.getId()==R.id.edit_service)
			editService.setCursorVisible(true);
		/*else if(v.getId()==R.id.cancel)
			servicesPresenter.onCancel(hashtable);
		else if(v.getId()==R.id.apply)
			servicesPresenter.onUpdate(hashtable);*/
		else if(v.getId()==R.id.next)
		{
			Intent intent=new Intent(this,ShowAdminAccount.class);
			finish();
			startActivity(intent);
			editor.putString("nextStep","account");
			editor.apply();
		}


	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		servicesPresenter.onDelete(( adapter.getItem(position)).getName());
		return false;
	}

	/*@Override
	public void beforeTextChanged(@NonNull CharSequence s, int start, int count, int after) {
oldService=s.toString();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(@NonNull Editable s) {
		newService=s.toString();
		servicesPresenter.check(oldService,newService);
		
	}*/

	@Override
	public void onFocusChange(@NonNull View v, boolean hasFocus) {
//if(v.hasFocus())
	v.setBackgroundColor(Color.WHITE);
	}
}