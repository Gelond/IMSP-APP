package uac.imsp.clockingapp.Controller.control;

import androidx.annotation.NonNull;

import uac.imsp.clockingapp.Controller.util.IClockingController;
import uac.imsp.clockingapp.View.util.IFingerprintAttendanceView;

public class FingerprintAttendanceController  extends
		ClockingController implements IClockingController {
	public FingerprintAttendanceController
			(@NonNull IFingerprintAttendanceView fingerprintAttendanceView) {
		super(fingerprintAttendanceView);
	}

	@Override
	public void onAuthSuccess(byte[] liveFingerprintData) {
		super.onAuthSuccess(liveFingerprintData);
	}

	@Override
	public void onClocking(int number) {
		super.onClocking(number);
	}
}
