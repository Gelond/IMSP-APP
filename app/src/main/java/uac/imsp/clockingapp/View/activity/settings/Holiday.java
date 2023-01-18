package uac.imsp.clockingapp.View.activity.settings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
		holidayController=new HolidayController(this);

		final Button addHoliday=findViewById(R.id.add_holiday);
		addHoliday.setOnClickListener(this);



	}

	@Override
	public void isHoliday() {
new ToastMessage(this,"");
	}

	@Override
	public void update() {

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
		}

	}
}