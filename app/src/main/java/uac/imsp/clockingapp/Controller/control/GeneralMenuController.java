package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import dao.EmployeeManager;
import dao.ServiceManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IGeneralMenuController;
import uac.imsp.clockingapp.View.util.IGeneralMenuView;

public class GeneralMenuController  implements IGeneralMenuController {

    private final IGeneralMenuView menuView;
    private Employee employee;
    private EmployeeManager employeeManager;


    public GeneralMenuController(IGeneralMenuView menuView){
        this.menuView=menuView;


    }


    @Override
    public void onSearchEmployeeMenu(int currentUser) {
        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);



        menuView.onSearchEmployeeMenuSuccessfull();
   employeeManager.close();
    }






    @Override
    public boolean onRegisterEmployeeMenu(int currentUser) {
        ServiceManager serviceManager=new ServiceManager((Context) menuView);
        serviceManager.open();
        String [] services=serviceManager.getAllServices();



        if(services.length==0)//the list is empty

             return false;




        menuView.onRegisterEmployeeMenuSuccessful();

      return true;
    }

    @Override
    public void onClocking() {
        menuView.onClocking();

    }

    @Override
    public void onConsultatisticsMenu(int currentUser) {
      /*  employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);*/


            menuView.onConsultatisticsMenuSuccessful();
        //employeeManager.close();

    }





    @Override
    public void onExit() {
        menuView.onExit();

    }
}


