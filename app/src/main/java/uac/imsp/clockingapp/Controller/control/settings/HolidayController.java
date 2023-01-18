package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;

import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import uac.imsp.clockingapp.Controller.util.settings.IHolidayController;
import uac.imsp.clockingapp.View.util.settings.IHolidayView;

public class HolidayController implements IHolidayController {
	IHolidayView holidayView;
	private Context context;
	public HolidayController(IHolidayView holidayView){
		this.holidayView=holidayView;
		context=(Context) this.holidayView;


	}

	@Override
	public void onDateSelected(String date) {
		DayManager dayManager=new DayManager(context);
		Day day=new Day(date);
		dayManager.open();
		dayManager.create(day);
		if(dayManager.isHoliday(day))
			holidayView.isHoliday();
		else
		{
			dayManager.setHoliday(day);
			EmployeeManager employeeManager=new EmployeeManager(context);
			employeeManager.open();
			employeeManager.holiday(day);
			employeeManager.close();
			holidayView.update();

		}
		dayManager.close();
	}

}
