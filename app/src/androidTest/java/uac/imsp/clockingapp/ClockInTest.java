package uac.imsp.clockingapp;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import entity.Day;
import uac.imsp.clockingapp.Controller.control.ClockingInOutController;
import uac.imsp.clockingapp.View.util.IClockInOutView;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ClockInTest implements IClockInOutView {
	private final ClockingInOutController clockingInOutPresenter ;
	Day day;String  time;
	public ClockInTest() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		clockingInOutPresenter = new ClockingInOutController(this, appContext);
		assertNotNull(clockingInOutPresenter);
	}
	@Test
 	public void test(){
		new ClockInTest();
		assertNotNull(clockingInOutPresenter);


	}
	public void testClocking(String date, String time){
		assertNotNull(clockingInOutPresenter);
		clockingInOutPresenter.clock(1,date,time);

	}
	@Test
	public void testWith_12_5_2022(){

		day=new Day(12,5);
		time="07:00";
		testClocking(day.getDate(),time);
	}

	@Test
	public void testWith_12_7_2022(){

		day=new Day(12,7);
		time="07:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_12_8_2022(){

		day=new Day(12,8);
		time="07:48";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_12_9_2022(){

		day=new Day(12,9);
		time="08:45";
		testClocking(day.getDate(),time);
	}

	@Test
	public void testWith_12_13_2022(){

		day=new Day(12,13);
		time="08:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_12_14_2022(){

		day=new Day(12,14);
		time="09:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_01_12_2022(){

		day=new Day(12,1);
		time="08:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_02_12_2022(){

		day=new Day(12,2);
		time="08:01";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_03_12_2022(){

		day=new Day(12,3);
		time="08:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_04_12_2022(){

		day=new Day(12,4);
		time="07:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_05_12_2022(){

		day=new Day(12,5);
		time="05:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_06_12_2022(){

		day=new Day(12,6);
		time="08:06";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testFrom_01_12_2022_To_06_12_2012(){
		testWith_01_12_2022();
		testWith_02_12_2022();
		testWith_03_12_2022();
		testWith_04_12_2022();
		testWith_05_12_2022();
		testWith_06_12_2022();
	}
	@Test
	public void testWith_12_12_2022(){
		day=new Day(12,12);
		time="09:16";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_12_15_2022(){
		day=new Day(12,15);
		time="10:00";
		testClocking(day.getDate(),time);
	}
	@Test
	public void testWith_12_16_2022(){
		day=new Day(12,16);
		time="06:00";
		testClocking(day.getDate(),time);
	}



	@Override
	public void onLoad() {

	}

	@Override
	public void onClockInSuccessful() {


	}

	@Override
	public void onClockOutSuccessful() {

	}

	@Override
	public void onClockingError(int errorNumber) {

	}

	@Override
	public void onSwitchCamera() {

	}
}
