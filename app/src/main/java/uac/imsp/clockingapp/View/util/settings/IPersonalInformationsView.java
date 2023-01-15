package uac.imsp.clockingapp.View.util.settings;

public interface IPersonalInformationsView {
	void onError(int errorNumber);

	void onUpdateSuccessfull();

	void onStart(String firstname, String lastname, String mailAddress);
}
