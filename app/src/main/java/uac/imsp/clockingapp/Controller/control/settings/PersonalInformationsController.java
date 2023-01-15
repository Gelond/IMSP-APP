package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.Objects;

import dao.EmployeeManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IPersonalInformationsController;
import uac.imsp.clockingapp.View.util.settings.IPersonalInformationsView;

public class PersonalInformationsController implements IPersonalInformationsController {
	IPersonalInformationsView personalInformationsView;
	Context context;
	Employee employee;

	public PersonalInformationsController(IPersonalInformationsView personalInformationsView){

		this.personalInformationsView=personalInformationsView;
		 context = (Context) personalInformationsView;

	}

	@Override
	public void onUpdate(String email) {
     if(TextUtils.isEmpty(email))
		 personalInformationsView.onError(0);
	 else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
		 personalInformationsView.onError(1);
	 else if(Objects.equals(employee.getMailAddress(), email))
		 personalInformationsView.onError(2);
	 else	{
		 EmployeeManager employeeManager=new EmployeeManager(context);
		 employeeManager.open();
		 employeeManager.update(employee,email);
		 employeeManager.close();
		 personalInformationsView.onUpdateSuccessfull();
	 }

	}

	@Override
	public void onStart(int number) {
		employee=new Employee(number);
		EmployeeManager employeeManager=new EmployeeManager(context);
		employeeManager.open();
		employeeManager.setInformations(employee);
		employeeManager.close();
		personalInformationsView.onStart(employee.getFirstname(),employee.getLastname(),
				employee.getMailAddress());


	}
}
