package uac.imsp.clockingapp.View.util.settings;

public interface IHolidayView {
	void isHoliday();

	void update();

	void onConsultHolidays(String fileName);

	void onNoAppFound();
}
