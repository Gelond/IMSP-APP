package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Enumeration;
import java.util.Hashtable;

import dao.ClockingManager;
import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IClockingController;
import uac.imsp.clockingapp.Controller.util.IFingerprintAttendanceController;
import uac.imsp.clockingapp.FingerprintHelper;
import uac.imsp.clockingapp.View.util.IFingerprintAttendanceView;
import uac.imsp.clockingapp.View.util.IClockInOutView;

public class ClockingController implements IClockingController, IFingerprintAttendanceController {


	protected  IClockInOutView clockInOutView;
	protected  IFingerprintAttendanceView fingerprintAttendanceView;
	protected final Context context;
	public ClockingController(@NonNull IClockInOutView clockInOutView) {

		this.clockInOutView=clockInOutView;
		this.context= (Context) this.clockInOutView;

	}

	public ClockingController(@NonNull IFingerprintAttendanceView fingerprintAttendanceView) {

		this.fingerprintAttendanceView=fingerprintAttendanceView;
		this.context= (Context) this.fingerprintAttendanceView;

	}



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

					employeeManager.setAttendance(employee,"Absent",date);
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

	public ClockingController(@NonNull IClockInOutView clockInOutView, Context context) {
		this.clockInOutView=clockInOutView;
		this.context=context;
		clockInOutView.onLoad();

	}
	@Override
	public void onClocking(int number) {

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
	public void onAuthSuccess(byte[] liveFingerprintData) {
		FingerprintHelper fingerprintHelper=new FingerprintHelper();
		EmployeeManager employeeManager=new EmployeeManager(context);
		employeeManager.open();
		Hashtable<Integer, byte[]> fingdata = employeeManager.getFingerprints();
		Enumeration<Integer> enumeration=fingdata.keys();


		Employee employee;
		Day day;
		ClockingManager clockingManager;
		DayManager dayManager;
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
		int number;
		while (enumeration.hasMoreElements())
		{
			number=enumeration.nextElement();
			if(fingerprintHelper.compareData(
					fingdata.get(number), liveFingerprintData)
			)
			{

				employee=new Employee(number);

				//Connection to clocking table
				clockingManager = new ClockingManager((Context) clockInOutView);
				//open  clocking connection
				clockingManager.open();
				if (clockingManager.hasNotClockedIn(employee, day)) {


					clockingManager.clockIn(employee, day);

					fingerprintAttendanceView.onClockInSuccessful();
				}
				else if (clockingManager.hasNotClockedOut(employee, day)) {
					clockingManager.clockOut(employee, day);
					fingerprintAttendanceView.onClockOutSuccessful();
				}
				else {
					fingerprintAttendanceView.onClockingError(3);
				}
				//close  clocking connection
				clockingManager.close();
				break;
			}
			}
		employeeManager.close();
		}
	}

