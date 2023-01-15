package uac.imsp.clockingapp.Controller.control.settings.others;

import uac.imsp.clockingapp.Controller.util.settings.others.IClockingController;
import uac.imsp.clockingapp.View.util.settings.others.IClockingView;

public class ClockingController implements IClockingController {
	IClockingView clockingView;

	public ClockingController(IClockingView clockingView)
	{
		this.clockingView=clockingView;

	}
	@Override
	public void onUseQRCode() {

		clockingView.onUseQRCode();
	}

	@Override
	public void onUseFingerPrint() {
		clockingView.onUseFingerPrint();

	}
}
