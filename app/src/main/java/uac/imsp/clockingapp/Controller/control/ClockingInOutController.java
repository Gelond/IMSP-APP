package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import androidx.annotation.NonNull;

import dao.ClockingManager;
import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.View.util.IClockInOutView;

public class ClockingInOutController
        implements IClockInOutController {

private final IClockInOutView clockInOutView;
private final Context context;
    public ClockingInOutController(@NonNull IClockInOutView clockInOutView) {

        this.clockInOutView=clockInOutView;
        this.context= (Context) this.clockInOutView;


    }

    public ClockingInOutController(@NonNull IClockInOutView clockInOutView, Context context) {
        this.clockInOutView=clockInOutView;
        this.context=context;
        clockInOutView.onLoad();

    }

    //used for android unit test
    public void clock(int number,String date,String time){
        Employee employee;
        Day day;
        EmployeeManager employeeManager;
        ClockingManager clockingManager;
        DayManager dayManager;
        employee = new Employee(number);
        //connection to  database , employee table
        employeeManager = new EmployeeManager(context);
        //open employee connection

        day=new Day(date);
        dayManager=new DayManager(context);
        dayManager.open();
        //A day is created and has an id
        dayManager.create(day);
        dayManager.close();

        employeeManager.open();
        if (!employeeManager.exists(employee))
            //employee does not exist
            clockInOutView.onClockingError(1);

        else //The employee exists
        {

            //check if the employee shouldWorkToday or not
            //case not
            if (employeeManager.shouldNotWorkThatDay(employee,day))
                clockInOutView.onClockingError(2);
            else {

                //Connection to clocking table
                clockingManager = new ClockingManager(context);
                //open  clocking connection
                clockingManager.open();
                if (clockingManager.hasNotClockedIn(employee, day))
                {

                    employeeManager.setAttendance(employee,"Abssent",date);
                    clockingManager.clockIn(employee, day,time);

                    clockInOutView.onClockInSuccessful();
                } else if (clockingManager.hasNotClockedOut(employee, day)) {
                    clockingManager.clockOut(employee, day);
                    clockInOutView.onClockInSuccessful();
                } else {
                    clockInOutView.onClockingError(3);
                }
                //close  clocking connection
                clockingManager.close();


            }
        }
        employeeManager.close();


    }

        @Override
    public void onClocking(int number) {
        //Take  time into account later
            Employee employee;
            Day day;
            EmployeeManager employeeManager;
            ClockingManager clockingManager;
            DayManager dayManager;

            employee = new Employee(number);
            //connection to  database , employee table
            employeeManager = new EmployeeManager(context);
            //open employee connection

            day=new Day();
            dayManager=new DayManager(context);
            dayManager.open();
            //A day is created and has an id
            dayManager.create(day);
            dayManager.close();

            employeeManager.open();
            if (!employeeManager.exists(employee))
                //employee does not exist
                clockInOutView.onClockingError(1);

            else //The employee exists
            {

                //check if the employee shouldWorkToday or not
                //case not
                if (employeeManager.shouldNotWorkToday(employee))
                    clockInOutView.onClockingError(2);
                else {

                    //Connection to clocking table
                    clockingManager = new ClockingManager((Context) clockInOutView);
                    //open  clocking connection
                    clockingManager.open();
                    if (clockingManager.hasNotClockedIn(employee, day)) {


                        clockingManager.clockIn(employee, day);

                        clockInOutView.onClockInSuccessful();
                    }
                    else if (clockingManager.hasNotClockedOut(employee, day)) {
                        clockingManager.clockOut(employee, day);
                        clockInOutView.onClockOutSuccessful();
                    }
                    else {
                        clockInOutView.onClockingError(3);
                    }
                    //close  clocking connection
                    clockingManager.close();

                }
            }
            //close employee management connection
            employeeManager.close();

        }

    @Override
    public void onSwitchCamara() {
    clockInOutView.onSwitchCamera();
    }

}
