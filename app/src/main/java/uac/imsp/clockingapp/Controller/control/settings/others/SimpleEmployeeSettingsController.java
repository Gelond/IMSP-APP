package uac.imsp.clockingapp.Controller.control.settings.others;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeSettingsController;
import uac.imsp.clockingapp.View.util.settings.ISimpleEmployeeSettingsView;

public class SimpleEmployeeSettingsController implements ISimpleEmployeeSettingsController {
	private Context context;
	private  ISimpleEmployeeSettingsView simpleEmployeeSettingsView;
	public SimpleEmployeeSettingsController(ISimpleEmployeeSettingsView simpleEmployeeSettingsView) {
	 this.simpleEmployeeSettingsView=simpleEmployeeSettingsView;
	 context= (Context) this.simpleEmployeeSettingsView;
	}

	@Override
	public void onMyAccount() {
		simpleEmployeeSettingsView.onAccount();

	}

	@Override
	public void onPersonalInfos() {
simpleEmployeeSettingsView.onPersonalInfos();
	}

	@Override
	public void onUserDocs() {
simpleEmployeeSettingsView.onUserDocs();
	}

	@Override
	public void onDarkMode() {
simpleEmployeeSettingsView.onDarkMode();
	}

	@Override
	public void onLanguage() {
simpleEmployeeSettingsView.onLanguage();
	}

	@Override
	public void onProblem() {
simpleEmployeeSettingsView.onProblem();
	}

	@Override
	public void onHelp() {
		simpleEmployeeSettingsView.onHelp();
	}

	@Override
	public void onSaveFingerprint() {
simpleEmployeeSettingsView.onSaveFingerprint();
	}
}
