package uac.imsp.clockingapp;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import dao.EmployeeManager;


@RunWith(AndroidJUnit4ClassRunner.class)
public class UnitTest {

	private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

	public UnitTest(){
		assertNotNull(appContext);

	}
	@Test
	public void test(){
		new UnitTest();
		EmployeeManager employeeManager=new EmployeeManager(appContext);
		employeeManager.open();

	}
}