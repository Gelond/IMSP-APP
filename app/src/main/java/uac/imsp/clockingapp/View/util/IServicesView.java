package uac.imsp.clockingapp.View.util;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.util.ServiceResuslt;

public interface IServicesView {
	void onNoServiceFound();
	void onServiceFound(ArrayList<ServiceResuslt> list);

	void askConfirmDelete();
	//void onUpdateError(int errorNumber,int serviceIndex);
	void onDeleteSucessful();
	void onDeleteError(int errorNumber);
	//void onServiceEdited();
	void onAddServiceError(int errorNumber);
	void onServiceAdded();
	//void askConfirmCancel();
	//void askConfirmUpdate();
	//void onNothingUpdated();
}
