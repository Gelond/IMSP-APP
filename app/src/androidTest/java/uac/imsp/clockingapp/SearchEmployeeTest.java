package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.control.SearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;
@RunWith(AndroidJUnit4ClassRunner.class)
public class SearchEmployeeTest implements IsearchEmployeeView {

    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final SearchEmployeeController searchEmployeeController;

    public  SearchEmployeeTest(){
        searchEmployeeController=new SearchEmployeeController(this,
                appContext);
        assertNotNull(searchEmployeeController);
    }
    @Test
    public void testSearchEmployee(){
        new SearchEmployeeTest();
        assertNotNull(searchEmployeeController);
        searchEmployeeController.onStart();
        searchEmployeeController.onSearch("1");
        searchEmployeeController.onSearch("");

    }
    @Override
    public void onNoEmployeeFound() {
        String message= appContext.getString(R.string.no_employee);
        assertEquals("ghj",message);

    }

    @Override
    public void onEmployeeFound(ArrayList<Result> list) {
        assertNotNull(list);
        assertNotEquals(0,list.size());

    }

    @Override
    public void onEmployeeSelected() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onPresenceReport() {

    }

    @Override
    public void onStatistics() {

    }
}

