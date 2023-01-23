package uac.imsp.clockingapp.View.util;

public interface IFingerprintAttendanceView {
	void onClockingError(int errorNumber);

	void onClockInSuccessful();

	void onClockOutSuccessful();
}
