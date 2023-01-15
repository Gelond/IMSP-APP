package uac.imsp.clockingapp.Controller.control;

import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeMenuController;
import uac.imsp.clockingapp.View.util.ISimpleEmployeeMenuView;

public class SimpleEmployeeMenuController  implements ISimpleEmployeeMenuController {
	ISimpleEmployeeMenuView simpleEmployeeMenuView;
	//private final Context context;


	public SimpleEmployeeMenuController(ISimpleEmployeeMenuView simpleEmployeeMenuView){
this.simpleEmployeeMenuView=simpleEmployeeMenuView;
//context= (Context) this.simpleEmployeeMenuView;

	}

	@Override
	public void onClocking() {
		simpleEmployeeMenuView.onClocking();

	}

	@Override
	public void onConsultatisticsMenu() {
		simpleEmployeeMenuView.onConsultatisticsMenuSuccessful();

	}

	@Override
	public void onConsultPresentReport() {
		simpleEmployeeMenuView.onConsultPresenceReport();

	}

	@Override
	public void onExit() {
		simpleEmployeeMenuView.onExit();

	}

	@Override
	public void onSettings() {
		simpleEmployeeMenuView.onSettings();

	}
}
