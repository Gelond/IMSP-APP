package uac.imsp.clockingapp.View.util;

public interface IClockInOutView{
    //GeneralMenu message on load
    void onLoad();

    //Message de succès de pointage
    void onClockInSuccessful();
    void onClockOutSuccessful();
    //Message d'erreur de pointage ou employe non retrouvé
    void onClockingError(int errorNumber);

	void onSwitchCamera();
}

