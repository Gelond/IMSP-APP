package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.LoginController.CurrentEmployee;

import android.content.Context;

import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReportController implements IConsultPresenceReportController {
    private final IConsultPresenceReportView consultPresenceReportView;
    private Employee employee;
    private final Context context;
    //private int Month,Year;
    private Day day;
    private final Day currentDay;
    int cpt=0;

    public ConsultPresenceReportController(IConsultPresenceReportView consultPresenceReportView){

        this.consultPresenceReportView=consultPresenceReportView;
        this.context=(Context) consultPresenceReportView;
         day =new Day();
         currentDay=day;


    }

    @Override
    public void onConsultPresenceReport(int number) {
        Day d;

        String [] state;
        EmployeeManager employeeManager;
        boolean accessible =true;
        employee=new Employee(number);
        employeeManager = new EmployeeManager(context);
        employeeManager.open();
        employeeManager.retrieveAddDate(employee);
        //the month following the current one
        d=new Day();


        if (day.addMonth().getMonthPart().compareTo(currentDay.getMonthPart()) > 0) {
            consultPresenceReportView.onReportNotAccessible(true);
            accessible =false;
        }
        else
            consultPresenceReportView.onReportAccessible(true);



        //the month preceding the current one
        if (day.subtractMonth().getMonthPart().
                compareTo(new Day(employee.getAddDate()).getMonthPart()) < 0) {
            consultPresenceReportView.onReportNotAccessible(false);
            accessible =false;
        }
        else
            consultPresenceReportView.onReportAccessible(false);
       if(accessible||cpt==0) {
    consultPresenceReportView.onStart(day.getDayOfWeek(),d.getDayOfWeek() ,
            d.getDayOfMonth(),day.getMonth(),day.getYear());


    state = employeeManager.getPresenceReportForEmployee(employee,
            day.getMonth(), day.getYear());


    consultPresenceReportView.onMonthSelected(state, day.getFirstDayOfMonth());
    cpt++;

}
        employeeManager.close();
        
    }

    @Override
    public void onPreviousMonth() {

                   if (day.getDayOfMonth() == 1)//we should go to the previous year
                ///moving to the december month of the previous year
                day = new Day(day.getYear() - 1, 12, 1);

            else//[february-december]
                //moving to the previous month of the current year
                day = new Day(day.getYear(), day.getMonth() - 1, 1);

            onConsultPresenceReport(employee.getRegistrationNumber());

          }

    @Override
    public void onNextMonth() {


        // we'll check if the next month is passed
       //

            //we'll check the current month
            //if it is december(12)

            if (day.getMonth() == 12)
                //we'll move to the january month of the new year
                day = new Day(day.getYear() + 1, 1, 1);
            else
                //we'll move to the next month of the current year
                day = new Day(day.getYear(), day.getMonth() + 1, 1);
            onConsultPresenceReport(employee.getRegistrationNumber());




    }

    @Override
    public void onConsultOwnStatistics()   {

        onConsultPresenceReport(CurrentEmployee);
    }


}
