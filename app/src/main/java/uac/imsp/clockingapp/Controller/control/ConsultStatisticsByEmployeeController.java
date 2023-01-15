package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Hashtable;

import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByEmployeeView;

public class ConsultStatisticsByEmployeeController implements
        IConsultStatisticsByEmployeeController {
    private Employee employee;
    private Day day;
    private final Day currentDay;
    int cpt=0;
    private final IConsultStatisticsByEmployeeView consultStatisticsByEmployeeView;
    public ConsultStatisticsByEmployeeController(IConsultStatisticsByEmployeeView
                                                 consultStatisticsByEmployeeView)
    {
        this.consultStatisticsByEmployeeView=consultStatisticsByEmployeeView;
        day=new Day();
        currentDay=day;

    }

    @Override
    public void onConsultStatisticsForEmployee(int number) {

        int total;
        boolean accessible =true;

        int p,a,r;
        Day d;
        Hashtable <Character,Float> stat;

        stat=new Hashtable<>();

        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultStatisticsByEmployeeView);

        employee=new Employee(number);
        employeeManager.open();
        employeeManager.retrieveAddDate(employee);
        //the month following the current one
        d=new Day();


        if (day.addMonth().getMonthPart().compareTo(currentDay.getMonthPart()) > 0) {
            consultStatisticsByEmployeeView.onReportNotAccessible(true);
            accessible =false;
        }
        else
            consultStatisticsByEmployeeView.onReportAccessible(true);



        //the month preceding the current one
        if (day.subtractMonth().getMonthPart().
                compareTo(new Day(employee.getAddDate()).getMonthPart()) < 0) {
            consultStatisticsByEmployeeView.onReportNotAccessible(false);
            accessible =false;
        }
        else
            consultStatisticsByEmployeeView.onReportAccessible(false);
        if(accessible|| this.cpt ==0) {
            consultStatisticsByEmployeeView.onStart(day.getDayOfWeek(), d.getDayOfWeek(),
                    d.getFirstDayOfMonth(), day.getMonth(), day.getYear());
            int [] attendanceReportForEmployee = employeeManager.getAttendanceReportForEmployee(employee,day.getMonth(),day.getYear());

            p= attendanceReportForEmployee[0];
            a= attendanceReportForEmployee[1];
            r= attendanceReportForEmployee[2];
            total= attendanceReportForEmployee[3];

            stat.put('P', 100*(float)p/ total);
            stat.put('A', 100*(float)a/ total);
            stat.put('R',100*(float) r/ total);



            consultStatisticsByEmployeeView.onMonthSelected(stat);

        }
        employeeManager.close();

    }


    @Override
    public void onPreviousMonth() {
        if(day.getDayOfMonth()==1)//we should go to the previous year
            ///moving to the december month of the previous year
            day=new Day(day.getYear()-1,12,1);

        else//[february-december]
            //moving to the previous month of the current year
            day=new Day(day.getYear(),day.getMonth()-1,1);

        onConsultStatisticsForEmployee(employee.getRegistrationNumber());

    }

    @Override
    public void onNextMonth() {
        //we'll check the current month
        //if it is december(12)
        if(day.getMonth()==12)
            //we'll move to the january month of the new year
            day=new Day(day.getYear()+1,1,1);
        else
            //we'll move to the next month of the current year
            day=new Day(day.getYear(),day.getMonth()+1,1);
        onConsultStatisticsForEmployee(employee.getRegistrationNumber());

    }
}
