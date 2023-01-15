package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.content.SharedPreferences;

import dao.EmployeeManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IShowAdminAcountController;
import uac.imsp.clockingapp.View.util.IShowAdminAcountView;

public class ShowAdminAcountController implements IShowAdminAcountController {
	IShowAdminAcountView showAdminAcountViev;
	private final Context context;
	public ShowAdminAcountController(IShowAdminAcountView showAdminAcountViev) {
		this.showAdminAcountViev=showAdminAcountViev;
		this.context=(Context) this.showAdminAcountViev;
	}


	@Override
	public void onLoad() {
		EmployeeManager employeeManager=new EmployeeManager(context);
		employeeManager.open();
		String [] account=employeeManager.retrieveAccount(new Employee(1));
		employeeManager.close();
		showAdminAcountViev.onStart(account[0],"Aa10%" );

	}

	@Override
	public void onCopyAccount() {
showAdminAcountViev.onCopyAccount();
	}

	@Override
	public void onNext() {
		final String PREFS_NAME="MyPrefsFile";
		SharedPreferences preferences= context.getSharedPreferences(PREFS_NAME,0);
	SharedPreferences.Editor editor=preferences.edit();
		editor.putString("nextStep","none");
		editor.apply();
		showAdminAcountViev.onNext();

	}


}