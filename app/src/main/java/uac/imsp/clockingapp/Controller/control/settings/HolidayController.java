package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import uac.imsp.clockingapp.Controller.util.settings.IHolidayController;
import uac.imsp.clockingapp.LocalHelper;
import uac.imsp.clockingapp.View.util.settings.IHolidayView;

public class HolidayController implements IHolidayController {
	IHolidayView holidayView;
	final String fileName="holidays";
	private final Context context;
	public HolidayController(IHolidayView holidayView){
		this.holidayView=holidayView;
		context=(Context) this.holidayView;


	}


	private void d(Day day){
		boolean isNew=false;
		try {
			File file = new File(context.getFilesDir(), fileName);
			if(!file.exists())
			{
				isNew=true;
				assert (file.createNewFile());
			}
			/*else
				file.setWritable(true);*/

			FileWriter writer = new FileWriter(fileName);

			if(isNew) {

				if (Objects.equals(LocalHelper.getSelectedLanguage(context), "fr"))
					writer.append("Jours fériés\n");
				else
					writer.append("Holidays\n");
			}

			writer.append(day.getDate()).append("\n");

			writer.flush();
			writer.close();
			//file.setReadOnly();


		} catch (IOException e) {
			e.printStackTrace();
		}
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
			Runnable runnable= () -> {

				dayManager.setHoliday(day);
				EmployeeManager employeeManager=new EmployeeManager(context);
				employeeManager.open();
				employeeManager.holiday(day);
				employeeManager.close();
				d(day);

			};
			AsyncTask.execute(runnable);
			holidayView.update();

		}
		dayManager.close();
	}

	@Override
	public void onConsultHolidays() {
     holidayView.onConsultHolidays(fileName);
	}

	@Override
	public void onNoAppFound() {
holidayView.onNoAppFound();
	}

}
