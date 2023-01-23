package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;

import dao.EmployeeManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.settings.ISaveFingerprintController;
import uac.imsp.clockingapp.FingerprintHelper;
import uac.imsp.clockingapp.View.util.settings.ISaveFingerprintView;

public class SaveFingerprintController implements ISaveFingerprintController {
	ISaveFingerprintView saveFingerprintView;
	private final Context context;
	public SaveFingerprintController(ISaveFingerprintView saveFingerprintView){
		this.saveFingerprintView=saveFingerprintView;
		context=(Context) this.saveFingerprintView;

	}



	@Override
	public void onFingerprintEnrollement(int currentUser, byte[] fingerprintData) {
		EmployeeManager employeeManager=new EmployeeManager(context);
		Employee employee=new Employee(currentUser);
		employeeManager.open();
		FingerprintHelper fingerprintHelper=new FingerprintHelper();
		byte[] encryptedFingerprintData =
				fingerprintHelper.
				encrypt(fingerprintData);
		employeeManager.updateFingerprint(employee,encryptedFingerprintData);
		saveFingerprintView.onSuccess();

	}
}
