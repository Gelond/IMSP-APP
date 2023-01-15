package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import dao.ServiceManager;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IServicesController;
import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.View.util.IServicesView;

public class ServicesController  implements IServicesController {
	private final IServicesView servicesView;
	private final Context context;
	private  Service currentService;
	//Hashtable<Integer,String> stringHashtable=new Hashtable<>();

	public ServicesController(IServicesView servicesView){
		this.servicesView=servicesView;
		this.context=(Context) this.servicesView;
	}


	/*@Override
	public void onUpdate(@NonNull Hashtable<Integer, String> hashtable) {

		if(hashtable.size()==0)
			servicesView.onNothingUpdated();
		else {
			servicesView.askConfirmUpdate();
			stringHashtable=hashtable;

		}



	}*/

	/*@Override
	public void onCancel(@NonNull Hashtable<Integer, String> hashtable) {
		if(hashtable.size()==0)
			servicesView.onNothingUpdated();
        else
		    servicesView.askConfirmCancel();

	}*/


	@Override
	public void onDelete(String serviceName) {
		this.currentService =new Service(serviceName);
		servicesView.askConfirmDelete();


	}

	@Override
	public void onDeleteConfirm(boolean confirmed) {
		Runnable runnable;

		if(confirmed)
		{

			ServiceManager serviceManager=new ServiceManager(context);
			serviceManager.open();
			if(!serviceManager.exists(currentService))
				servicesView.onDeleteError(0);
			else if(serviceManager.hasEmployee(currentService))
			servicesView.onDeleteError(1);
			else {

				 runnable= () -> serviceManager.delete(currentService);
				AsyncTask.execute(runnable);

				servicesView.onDeleteSucessful();
			}


			serviceManager.close();
		}

	}

	@Override
	public void onStart() {
		int id;
		String name;
		//String service;
		ArrayList<ServiceResuslt> list= new ArrayList<>();
		ServiceResuslt result;


		Service[] services;
		ServiceManager serviceManager;

		serviceManager=new ServiceManager(context);
		serviceManager.open();

		services = serviceManager.getAllService();
		serviceManager.close();

		if(services.length==0)
			servicesView.onNoServiceFound();
		else
		{
			for (Service service : services) {
				id = service.getId();
				name = service.getName();

				result = new ServiceResuslt(id );
				result.setName(name);
				list.add(result);
			}
			servicesView.onServiceFound(list);


		}



	}

	/*@Override
	public void check(String oldService, String newService) {

		if(!Objects.equals(oldService, newService))
			servicesView.onServiceEdited();
			}*/

	@Override
	public void onAddService(String service) {
		Service service1=new Service(service);
		ServiceManager serviceManager=new ServiceManager(context);
		serviceManager.open();
		if(service.equals(""))
			servicesView.onAddServiceError(0);
		else if(serviceManager.exists(service1))
			servicesView.onAddServiceError(1);
		else
		{
			serviceManager.create(service1);
			servicesView.onServiceAdded();
		}
		serviceManager.close();


	}

	/*@Override
	public void onUpdateConfirmed() {
		Enumeration<Integer> ids = stringHashtable.keys();
		int id;

		Service service;
		String s;

		ServiceManager serviceManager = new ServiceManager(context);
		serviceManager.open();
		while (ids.hasMoreElements()) {
			id = ids.nextElement();
			s = stringHashtable.get(id);
			if (Objects.equals(s, "")) {
				servicesView.onUpdateError(0, id);
				break;
			}
			service = new Service(s);
			if (serviceManager.exists(service)) {
				servicesView.onUpdateError(1, id);

				break;
			}
			service = new Service(id);
			serviceManager.update(service, s);
		}
		serviceManager.close();

	}*/
}
