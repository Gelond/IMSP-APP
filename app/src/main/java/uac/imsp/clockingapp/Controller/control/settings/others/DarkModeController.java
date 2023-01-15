package uac.imsp.clockingapp.Controller.control.settings.others;

import uac.imsp.clockingapp.Controller.util.settings.others.IDarkModeController;
import uac.imsp.clockingapp.View.util.settings.others.IDarkModeView;

public class DarkModeController implements IDarkModeController {
	IDarkModeView darkModeView;

	public DarkModeController(IDarkModeView darkModeView){
		this.darkModeView=darkModeView;


	}

	@Override
	public void onDarkMode() {
darkModeView.onDarkMode();
	}
}
