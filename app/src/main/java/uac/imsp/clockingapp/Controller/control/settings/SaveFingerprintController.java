package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.settings.ISaveFingerprintController;
import uac.imsp.clockingapp.View.util.settings.ISaveFingerprintView;

public class SaveFingerprintController implements ISaveFingerprintController {
	ISaveFingerprintView saveFingerprintView;
	private Context context;
	public SaveFingerprintController(ISaveFingerprintView saveFingerprintView){
		this.saveFingerprintView=saveFingerprintView;
		context=(Context) this.saveFingerprintView;

	}
}
