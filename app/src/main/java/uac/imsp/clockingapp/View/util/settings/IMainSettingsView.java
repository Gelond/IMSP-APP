package uac.imsp.clockingapp.View.util.settings;

public interface IMainSettingsView {
	void onShareApp();
	void onShareAppViaQRCode();
	void onAppcacheCleared();
	void onName(String title,String msg,String pos,String neg);
	void onEmail(String title,String msg,String pos,String neg);
	void onDescription(String title,String msg,String pos,String neg);

	void onOverview();
	void onAccount();
	void onService();

	void onClock();
	void onDark();
	void onLanguage();
	void onProblem();
	void onHelp();

	void onLogout();

	void onSwitch();
}
