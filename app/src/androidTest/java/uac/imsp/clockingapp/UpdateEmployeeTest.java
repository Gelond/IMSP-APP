package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.control.UpdateEmployeeController;
import uac.imsp.clockingapp.View.util.IUpdateEmployeeView;

@RunWith(AndroidJUnit4ClassRunner.class)
public class UpdateEmployeeTest  implements IUpdateEmployeeView {
    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final UpdateEmployeeController updateEmployeePresenter;
    public UpdateEmployeeTest(){
        Hashtable<String,Object> informations=new Hashtable<>();

        updateEmployeePresenter=new UpdateEmployeeController(this,appContext);
        assertNotNull(updateEmployeePresenter);
        //updateEmployeePresenter.onLoad( 1,  informations) ;
        assertNotNull(informations);
    }

    @Override
    public void onSomethingchanged() {

    }
    @Test
    public void testUpdateEmployee(){

        new UpdateEmployeeTest();
        assertNotNull(updateEmployeePresenter);
        updateEmployeePresenter.onUpdateEmployee("","Direction",
                0,0,null,null,"");


        updateEmployeePresenter.onUpdateEmployee("kkg","Direction",
                0,0,null,null,"");

        updateEmployeePresenter.onUpdateEmployee("ezestar@gmail.com","Direction",
                0,0,null,null,"");


    }

    @Override
    public void onNothingChanged() {
        //assertEquals("jbk",message);

    }


    @Override
    public void onUpdateEmployeeError(int errorNumber) {
        /*assertTrue(Objects.equals(errorNumber, "Email requis !") ||
                Objects.equals(errorNumber, "Email invalide !"));*/




    }


    @Override
    public void askConfirmUpdate() {
        String pos= appContext.getString(R.string.yes);
        String neg= appContext.getString(R.string.no);
        String title= appContext.getString(R.string.confirmation);
        String message= appContext.getString(R.string.confirmation_update);
        assertEquals("Oui",pos);
        assertEquals("Non",neg);
        assertEquals("Confirmation",title);
        assertEquals("Voulez vous vraiment appliquer ces modifications ?",message);


    }
}

