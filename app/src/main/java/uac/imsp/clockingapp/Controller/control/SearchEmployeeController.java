package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.ArrayList;

import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.ISearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;

public class SearchEmployeeController  implements
        ISearchEmployeeController {
    private final IsearchEmployeeView searchEmployeeView ;
    private final Context context;
    public SearchEmployeeController(IsearchEmployeeView searchEmployeeView){
        this.searchEmployeeView=searchEmployeeView;
        this.context= (Context) this.searchEmployeeView;
    }
    public SearchEmployeeController(IsearchEmployeeView searchEmployeeView,
                                    Context context){
        this.searchEmployeeView=searchEmployeeView;
        this.context=context;
    }




    @Override
    public  void onSearch(String data) {
        int statusIndex;
        int number;
        String lastname,firstname,service;
        byte[] picture;
        ArrayList <Result>  list= new ArrayList<>();
        Result result;


        Employee[] employeeSet;
        EmployeeManager employeeManager;

        employeeManager=new EmployeeManager(context);
        employeeManager.open();

        employeeSet =employeeManager.search(data);

        if(employeeSet.length==0)
            searchEmployeeView.onNoEmployeeFound();
        else
        {
            for (Employee employee : employeeSet) {
                number = employee.getRegistrationNumber();
                lastname = employee.getLastname();
                firstname = employee.getFirstname();
                service = employeeManager.getService(employee).getName();
                statusIndex=employeeManager.getStatus(employee,new Day());
                picture=employee.getPicture();

                result = new Result(number, lastname, firstname, service,
                        statusIndex,picture);
                list.add(result);
            }
            searchEmployeeView.onEmployeeFound(list);


        }
            employeeManager.close();

    }

    @Override
    public void onEmployeeSelected(int number) {
        searchEmployeeView.onEmployeeSelected();

    }

    @Override
    public void onOptionSelected(int which) {
        if(which==0)
            searchEmployeeView.onUpdate();
        else if (which==1)
            searchEmployeeView.onDelete();
        else if (which==2)
            searchEmployeeView.onPresenceReport();
        else if(which==3)
            searchEmployeeView.onStatistics();

    }

    @Override
    public void onStart() {
        onSearch("*");

    }


}

