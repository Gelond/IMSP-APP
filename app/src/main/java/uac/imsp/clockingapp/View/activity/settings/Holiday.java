package uac.imsp.clockingapp.View.activity.settings;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Calendar;

import uac.imsp.clockingapp.Controller.control.settings.HolidayController;
import uac.imsp.clockingapp.Controller.util.settings.IHolidayController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;
import uac.imsp.clockingapp.View.util.settings.IHolidayView;

public class Holiday extends AppCompatActivity implements IHolidayView, View.OnClickListener {
	IHolidayController holidayController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holiday);

		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
		// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.holdays);

		holidayController=new HolidayController(this);

		final Button addHoliday=findViewById(R.id.add_holiday),
		 consult =findViewById(R.id.view_holidays);
		addHoliday.setOnClickListener(this);
		consult.setOnClickListener(this);




	}

	@Override
	public void isHoliday() {
new ToastMessage(this,"");
	}

	@Override
	public void update() {

	}

	@Override
	public void onConsultHolidays(String fileName) {
		File  holidays =new File(getFilesDir(),fileName);
		Uri fileUri= FileProvider.getUriForFile(Holiday.this,
				getApplicationContext().getPackageName()+".provider",
				holidays);

		Intent viewIntent = new Intent(Intent.ACTION_VIEW);
		// Set the data and type for the intent
		viewIntent.setDataAndType(fileUri, "text/plain");

// Verify that the intent will resolve to an activity
		//if (viewIntent.resolveActivity(getPackageManager()) != null) {
			// Start the activity
			startActivity(Intent.createChooser(viewIntent, "Share file"));
		//} /*else {*/

			//holidayController.onNoAppFound();
		}




	@Override
	public void onNoAppFound() {
		new ToastMessage(this, "No app found to open the file.");

	}

	@Override
	public void onClick(@NonNull View v) {
		if(v.getId()==R.id.add_holiday)
		{
			final Calendar cldr = Calendar.getInstance();
			int day = cldr.get(Calendar.DAY_OF_MONTH);
			int month = cldr.get(Calendar.MONTH);
			int year = cldr.get(Calendar.YEAR);
			DatePickerDialog picker = new DatePickerDialog(Holiday.this,
					(view, year1, monthOfYear, dayOfMonth) -> {

						//Birthdate.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year1));
						String date = "" + year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						holidayController.onDateSelected(date);

					},
					year, month, day);


			picker.show();
		} else if (v.getId()==R.id.view_holidays) {
			holidayController.onConsultHolidays();

		}


	}
}