package uac.imsp.clockingapp.Controller.util;

public interface IClockInOutController {

    //prend le matricule de l'employé en paramètre
    void onClocking(int number);

	void onSwitchCamara();
}
