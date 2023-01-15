package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Hashtable;
import java.util.Objects;

import dao.EmployeeManager;
import dao.ServiceManager;
import entity.Day;
import entity.Employee;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByServiceController;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByServiceView;

public class ConsultStatisticsByServiceController implements
        IConsultStatisticsByServiceController {



    private final IConsultStatisticsByServiceView consultStatisticsByServiceView;
    Hashtable<String, int[]> reportByService =new Hashtable<>();
   // private String startDate;
    private Day day;
    Service[] serviceSet;
    private final Day CurrentDay;
    int cpt=0;

    public ConsultStatisticsByServiceController(IConsultStatisticsByServiceView
                                                        consultStatisticsByServiceView)
    {
        this.consultStatisticsByServiceView=consultStatisticsByServiceView;
        day=new Day();
        CurrentDay=day;





    }

    @Override
    public void onConsultStatisticsByService() {

        Day d;
        boolean accessible=true;
        int [] repTable = new int[4];
      //  float [] rep = new float[3];
        int [] currentEmployeeReportTable;

        // Hashtable<String, float[]> report =new Hashtable<>();
        EmployeeManager employeeManager;
        employeeManager = new EmployeeManager((Context) consultStatisticsByServiceView);

        // employeeManager.close();

        ServiceManager serviceManager;
        serviceManager = new ServiceManager((Context) consultStatisticsByServiceView);
        serviceManager.open();
        serviceSet = serviceManager.getAllService();
        serviceManager.close();
        day=new Day(day.getYear(), day.getMonth(),1);
        d=new Day(day.getYear(), day.getMonth(), day.getLenthOfMonth());
        if(Objects.equals(day.getMonthPart(), CurrentDay.getMonthPart()))


consultStatisticsByServiceView.onStart(day.getDayOfWeek(), CurrentDay.getDayOfWeek(),
        CurrentDay.getDayOfMonth(), CurrentDay.getMonth(), CurrentDay.getYear());
        else
            consultStatisticsByServiceView.onStart(day.getDayOfWeek(),d.getDayOfWeek(),
                    day.getLenthOfMonth(), day.getMonth(), day.getYear());


        if (serviceSet.length == 0)
            consultStatisticsByServiceView.onNoServiceFound();
        else {

            employeeManager.open();
            Employee[] employees = employeeManager.search("*");


            if (day.addMonth().getMonthPart().compareTo(CurrentDay.getMonthPart()) > 0) {
                consultStatisticsByServiceView.onReportNotAccessible(true);
                accessible = false;
            } else
                consultStatisticsByServiceView.onReportAccessible(true);


            //the month preceding the current one
            if (day.subtractMonth().getMonthPart().
                    compareTo(new Day("2022-12-01").getMonthPart()) < 0) {
                consultStatisticsByServiceView.onReportNotAccessible(false);
                accessible = false;
            } else
                consultStatisticsByServiceView.onReportAccessible(false);
            if (accessible || cpt == 0)
            {

                for (Service service : serviceSet) {
                    for (Employee employee : employees) {

                        if (Objects.equals(employeeManager.getService(employee).getName(),
                                service.getName())) {
                            currentEmployeeReportTable =
                                    employeeManager.getAttendanceReportForEmployee
                                            (employee, day.getMonth(), day.getYear());

                            for (int i = 0; i < 4; i++) {
                                repTable[i] += currentEmployeeReportTable[i];

                            }
                        }

                    }
                    reportByService.put(service.getName(), repTable);
                    repTable = new int[4];
                }

            cpt++;
        }

                       // report.put(service.getName(),rep);
                        consultStatisticsByServiceView.onServiceFound(onReportStatusChanged(0));
      employeeManager.close();
            }

            }



    @Override
    public Hashtable<String, Integer> onReportStatusChanged(int status) {
        Hashtable<String, Integer> r=new Hashtable<>();
        for(Service service:serviceSet)
            r.put(service.getName(),
                    Objects.
                            requireNonNull(reportByService.
                                    get(service.
                                            getName()))[status]);


        return r ;
    }




    @Override
    public void onPreviousMonth() {
        if(day.getDayOfMonth()==1)//we should go to the previous year
            ///moving to the december month of the previous year
            day=new Day(day.getYear()-1,12,1);

        else//[february-december]
            //moving to the previous month of the current year
            day=new Day(day.getYear(),day.getMonth()-1,1);

        onConsultStatisticsByService();



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
        onConsultStatisticsByService();


    }


    }




