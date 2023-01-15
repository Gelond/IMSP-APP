package uac.imsp.clockingapp.Controller.util.settings;

public interface IMainSettingsController {
	//about app
	void onShareApp();
	void onShareAppViaQRCode();
	void onOverview();
	void onAppversin();
	void onUsersDoc();
	void onAccount();
	void onLogout();
	void onClearAppCache();
	//About the establishment
	void onName();
	void onPicture();
	void onService();
	void onEmail();
	void onClocking();
	void onDescription();
	//employee account
	void onUsername();
	void onPassword();
	//notifications
	void onAdd();
	void onDelete();
	void onUpdate();
	//others
	void onLanguague();
	void onDarkMode();
	void onHelp();
	void onReportProblem();

	void onSwitch();
}
