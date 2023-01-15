package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import uac.imsp.clockingapp.Controller.control.ChangePasswordController;
import uac.imsp.clockingapp.View.util.IAccountView;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)

public class ExampleInstrumentedTest  implements IAccountView {
    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
   private final ChangePasswordController changePasswordPresenter;

    public ExampleInstrumentedTest(){
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        changePasswordPresenter=new ChangePasswordController( this,appContext);

    assertNotNull(changePasswordPresenter);
        changePasswordPresenter.onStart(1);
    }
    @Test
    public void useAppContext()  {
        // Context of the app under test.
        assertEquals("uac.imsp.clockingapp", appContext.getPackageName());
    }
   @Test
    public  void testChangePassword(){
        new ExampleInstrumentedTest();
        assertNotNull(changePasswordPresenter);
      // changePasswordPresenter.onStart(1);
       assertNotNull(changePasswordPresenter.getEmployee());
       assertNotNull(changePasswordPresenter.getEmployeeManager());
       assertEquals("User10",changePasswordPresenter.getEmployee().getUsername());
       assertEquals("password",changePasswordPresenter.getEmployee().getPassword());
       assertNotNull(changePasswordPresenter);
       testOnSubmitWhenUserDoesntEditsAnything();
       testOnSubmitWhenUserEditsOnlyOldPasswordField();
       testOnSubmitWhenUserEditsOnlyNewPasswordField();
       testOnSubmitWhenUserEditsAllFields();


    }


    @Override
    public void onSuccess() {
        String message= appContext.getString(R.string.password_changed_successfully);
        assertEquals("Mot de passe changé avec succès",message);

    }

    @Override
    public void onWrongPassword() {
        String  message= appContext.getString(R.string.password_invalid);
        assertTrue("Tous les champs sont requis !".equals(message)||
               "Mot de passe invalide !".equals(message)||
               "Mot de passe incorrect !".equals(message)||
               "Vérifiez le mot de passe précédent et reessayez !".equals(message)
       );
        // assertEquals("Tous les champs sont requis !",message);
        // assertEquals("Mot de passe invalide !",message);
        // assertEquals("Vérifiez le mot de passe précédent et reessayez !",message);
        //assertEquals("",message);










    }

    @Override
    public void onStart(String username) {
        assertEquals("User10",username);

    }
    //case the user doesn't enter anything
    @Test
    public void testOnSubmitWhenUserDoesntEditsAnything(){
        new ExampleInstrumentedTest();
       assertNotNull(changePasswordPresenter);
        changePasswordPresenter.onSubmit(null, null,null);
        changePasswordPresenter.onSubmit("","",null);
        changePasswordPresenter.onSubmit("","","");
       changePasswordPresenter.onSubmit(null,"","");
        changePasswordPresenter.onSubmit(null,"",null);
    }
    @Test
    public void testOnSubmitWhenUserEditsOnlyOldPasswordField(){

        new ExampleInstrumentedTest();
        assertNotNull(changePasswordPresenter);
        changePasswordPresenter.onSubmit("", null,null);
        changePasswordPresenter.onSubmit("", null,"");

        changePasswordPresenter.onSubmit("dfe",null,null);
        changePasswordPresenter.onSubmit("dfe",null,"");
        changePasswordPresenter.onSubmit("password",null,null);
        changePasswordPresenter.onSubmit("password",null,"");
        changePasswordPresenter.onSubmit("hjk","",null);
        changePasswordPresenter.onSubmit("hjk","","");


        changePasswordPresenter.onSubmit("password","",null);
        changePasswordPresenter.onSubmit("password","","");


    }

    @Test
    public void testOnSubmitWhenUserEditsOnlyNewPasswordField(){
        new ExampleInstrumentedTest();
        assertNotNull(changePasswordPresenter);
        changePasswordPresenter.onSubmit(null, "",null);
         changePasswordPresenter.onSubmit(null, "","");

        changePasswordPresenter.onSubmit(null,"jjdk",null);

        changePasswordPresenter.onSubmit(null,"passwordn",null);
        changePasswordPresenter.onSubmit(null,"passwordn","");
        changePasswordPresenter.onSubmit("","gr",null);

        changePasswordPresenter.onSubmit("","gr","");
        changePasswordPresenter.onSubmit("","passwordn",null);

        changePasswordPresenter.onSubmit("","passwordn","");
    }
    @Test
    public void testOnSubmitWhenUserEditsAllFields(){
        String newPassword,oldPassword;
        new ExampleInstrumentedTest();
        assertNotNull(changePasswordPresenter);
        //case all edited passwords are invalid
       changePasswordPresenter.onSubmit("gg","fgr","dfr");
       changePasswordPresenter.onSubmit("gg","fgr","password");


        // case only old password edited is invalid
        changePasswordPresenter.onSubmit("inv","passwordn","frt");
        changePasswordPresenter.onSubmit("gg","fgr","password");
        // case only new password edited is invalid and old password  edited is inCorrect
        changePasswordPresenter.onSubmit("passwordn","pwd","drd");
        changePasswordPresenter.onSubmit("passwordn","fgr","password");
        // case only new password edited is invalid and old password edited is correct
        changePasswordPresenter.onSubmit("password","pwd","pwd");
        changePasswordPresenter.onSubmit("password","fgr","password");

        /*case both old password edited  and new password
        are valid but old password edited is incorrect*/
       changePasswordPresenter.onSubmit("passwordo","passwordn","dfr");



        //case both old password edited  and new password are edited, valid and old password is correct
        newPassword="passwordn";
         oldPassword = "password";
         String swap;
         //case newPasswordConfirm doesn't match newPassword
        //changePasswordPresenter.onSubmit(oldPassword,newPassword,"otherpass");
        //test if the password is really not changed
        changePasswordPresenter.getEmployeeManager().setAccount(changePasswordPresenter.getEmployee());
        assertNotEquals(newPassword,changePasswordPresenter.getEmployee().getPassword());
        assertEquals(oldPassword,changePasswordPresenter.getEmployee().getPassword());

        //case newPasswordConfirm matches newPassword
        changePasswordPresenter.onSubmit(oldPassword,newPassword,newPassword);

        //test if the password is really changed
        changePasswordPresenter.getEmployeeManager().setAccount(changePasswordPresenter.getEmployee());
        assertEquals(newPassword,changePasswordPresenter.getEmployee().getPassword());

        swap=newPassword;
       newPassword=oldPassword;

        oldPassword=swap;

        //rechange
        changePasswordPresenter.onSubmit(oldPassword,newPassword,newPassword);





    }


}
