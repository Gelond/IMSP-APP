package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Hashtable;

import dao.EmployeeManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.control.DeleteEmployeeController;
import uac.imsp.clockingapp.View.util.IDeleteEmployeeView;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DeleteEmployeeTest  implements IDeleteEmployeeView {
    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
   private final DeleteEmployeeController deleteEmployeePresenter;
    public DeleteEmployeeTest(){
        deleteEmployeePresenter=new DeleteEmployeeController(this,appContext);
        assertNotNull(deleteEmployeePresenter);
        int number=1;
        Hashtable<String,Object> informations=new Hashtable<>();
        deleteEmployeePresenter.onLoad( number,  informations) ;
        assertNotNull(informations);
        assertTrue(informations.containsKey("number"));
        assertTrue(informations.containsKey("lastname"));
        assertTrue(informations.containsKey("firstname"));
        assertTrue(informations.containsKey("email"));
        assertTrue(informations.containsKey("username"));
        assertTrue(informations.containsKey("gender"));
        assertTrue(informations.containsKey("birthdate"));
        assertTrue(informations.containsKey("type"));
        assertTrue(informations.containsKey("service"));
        assertTrue(informations.containsKey("start"));
        assertTrue(informations.containsKey("end"));
        //assertTrue(informations.containsKey("picture")); false test failed
        assertEquals("1",informations.get("number"));
        assertEquals("AKOBA",informations.get("lastname"));
        assertEquals("Patrick",informations.get("firstname"));
        assertEquals("super@gmail.com",informations.get("email"));
        assertEquals("User10",informations.get("username"));
        assertEquals('M',informations.get("gender"));
        assertEquals("Directeur",informations.get("type"));
        assertEquals("Direction",informations.get("service"));
        assertEquals(8,informations.get("start"));
        assertEquals(17,informations.get("end"));
         assertEquals("01/01/1970",informations.get("birthdate"));

    }
    @Test
    public void testDeleteEmployee(){
        new DeleteEmployeeTest();
        assertNotNull(deleteEmployeePresenter);
        deleteEmployeePresenter.onDeleteEmployee(0);
        deleteEmployeePresenter.onConfirmResult(false);
        Employee employee=new Employee(1);
        EmployeeManager employeeManager=new EmployeeManager(appContext);
        employeeManager.open();
        assertTrue(employeeManager.exists(employee));
        deleteEmployeePresenter.onConfirmResult(true);
        assertFalse(employeeManager.exists(employee));
        employeeManager.close();



    }
    @Override
    public void onDeleteSuccessfull() {
        String message= appContext.getString(R.string.notify_delete_sucessfull);
        assertEquals("Employé supprimé avec succès",message);

    }

    @Override
    public void askConfirmDelete() {
       /* assertEquals("Oui",pos);
        assertEquals("Non",neg);
        assertEquals("Confirmation",title);
        assertEquals("Voulez vous vraiment supprimer l'employé ?",message);*/

    }

    @Override
    public void onError(int errorNumber) {

    }
}
