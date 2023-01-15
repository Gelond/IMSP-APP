package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import dao.EmployeeManager;
import dao.PlanningManager;
import dao.ServiceManager;
import entity.Employee;
import entity.Planning;
import uac.imsp.clockingapp.Controller.control.RegisterEmployeeController;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterEmployeeTest  implements IRegisterEmployeeView {
    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final RegisterEmployeeController registerEmployeePresenter;
    public RegisterEmployeeTest(){
        registerEmployeePresenter=new RegisterEmployeeController(this,appContext);
        assertNotNull(registerEmployeePresenter);
    }
   @Test
    public void testRegisterEmployee(){
        String [] services;
        new RegisterEmployeeTest();
        assertNotNull(registerEmployeePresenter);
        services= registerEmployeePresenter.onLoad();
        assertNotNull(registerEmployeePresenter.getServiceManager());
        assertNotNull(services);
        assertNotEquals(0,services.length);
         registerEmployeePresenter.onRegisterEmployee("","",
                "","M","","","",
                "","","",0,
                0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);

       registerEmployeePresenter.onRegisterEmployee("10","",
               "","M","","","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","q",
               "","M","","","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);

       registerEmployeePresenter.onRegisterEmployee("10","No",
               "","M","","","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "1","M","","","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22","","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22","mhgjhii","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","km",
               "","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "","","",0,
               0,null,"", new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "pass","","",0,
               0,null,"", new byte[]{'F','F','F','F','F','F','F'}, true);
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-29",
               "adedeezechiel@gmail.com","User100",
               "password","","",0,
               0,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);



       //case the employee already exists
       //regumber exists
       registerEmployeePresenter.onRegisterEmployee("1","No",
               "Jean","M","2022-08-22",
               "adl@gmail.com","User1000",
               "password","password","Aucun",8,
               18,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);
       //mail exists
       registerEmployeePresenter.onRegisterEmployee("100","No",
               "Jean","M","2022-08-22",
               "super@gmail.com","User1000",
               "password","password","Aucun",8,
               18,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);

     //username exists
       registerEmployeePresenter.onRegisterEmployee("100","No",
               "Jean","M","2022-08-22",
               "supr@gmail.com","User10",
               "password","password","Aucun",8,
               18,null,"",new byte[]{'F','F','F','F','F','F','F'}, true);


       //correct employee without picture
     /*  registerEmployeePresenter.onRegisterEmployee("10","No",

               "Jean","M","2022-08-22",

               "adedeezechiel@gmail.com","User100",
               "password","password","Direction",8,
               18,null,"",new byte[]{'F','F','F','F','F','F','F'});*/
       Employee employee=new Employee(10);
       EmployeeManager employeeManager=new EmployeeManager(appContext);
       assertNotNull(employeeManager);
       employeeManager.open();
       assertNotNull(employeeManager);
       assertTrue(employeeManager.exists(employee));
       employeeManager.setInformationsWithoutPiture(employee);
       assertNotNull(employee.getLastname());
       assertNotNull(employee.getFirstname());
       assertNotNull(employee.getBirthdate());
       assertNotNull(employee.getMailAddress());
       assertNotNull(employee.getUsername());
       assertNull(employee.getPassword());
       ServiceManager serviceManager=new ServiceManager(appContext);
       serviceManager.open();
       assertNotNull(serviceManager);
       String service;
       assertNotNull(employeeManager);
       service=employeeManager.getService(employee).getName();
       assertNotNull(service);
       assertEquals("Direction",service);
       PlanningManager planningManager=new PlanningManager(appContext);
       planningManager.open();
       assertNotNull(planningManager);
       Planning planning;
       planning=employeeManager.getPlanning(employee);
       assertNotNull(planning);
       assertEquals("08:00",planning.getStartTime());
       assertEquals("18:00",planning.getEndTime());

       //another employee
       /*registerEmployeePresenter.onRegisterEmployee("11","No",
               "Jean","M","2022-08-22",
               "echiel@gmail.com","User110",
               "password","password","Aucun",8,
               18,null,"",new byte[]{'F','F','F','F','F','F','F'});*/

   }
    @Override
    public void onRegisterEmployeeSuccess() {
        String message="";
        assertEquals("Employé enregistré avec succès",message);


    }
    @Override
    public void onRegisterEmployeeError(int errorNumber) {
        String message="";
        // assertEquals("Matricule requis !",message);
        // assertEquals("Nom requis !",message);
        //assertEquals("Nom invalide !",message);
        //assertEquals("Prénom requis !",message);
        // assertEquals("Prénom(s) invalide(s) !",message);
        //assertEquals("Email requis !",message);
        //assertEquals("Email invalide !",message);
        //assertEquals("Username requis !",message);
        //assertEquals("Username invalide !",message);
        //assertEquals("Mot de passe requis !",message);
        //assertEquals("Mot de passe invalide !",message);
        //assertEquals("Vérifier le mot de passe et resssayer !",message);
        //"Ce couriel a été déjà attribué à un employé !"

       //assertEquals("Ce matricule a été déjà attribué à un employé !",message);

        //assertEquals("Ce username a été déjà attribué à un employé !",message);
        //assertEquals("Ce mail a été déjà attribué à un employé !",message);

        assertTrue(message.equals("Matricule requis !") ||
                "Nom requis !".equals(message) ||
                "Nom invalide !".equals(message) ||
                "Prénom requis !".equals(message) ||
                "Prénom(s) invalide(s) !".equals(message) ||
                "Email requis !".equals(message) ||
                "Email invalide !".equals(message) ||
                "Username requis !".equals(message) ||
                "Username invalide !".equals(message) ||
                "Mot de passe requis !".equals(message) ||
                "Mot de passe invalide !".equals(message) ||
                "Vérifier le mot de passe et resssayer !".equals(message) ||
                "Ce matricule a été déjà attribué à un employé !".equals(message)||
                        "Ce couriel a été déjà attribué à un employé !".equals(message)||
                        "Ce username a été déjà attribué à un employé !".equals(message)

//
        );


    }

    @Override
    public void onShowHidePassword(int viewId, int eyeId) {

    }

    @Override
    public void sendEmail(String[] to, String qrCodeFileName, String lastname, String firstname, String username, String password, String gender) {

    }


}

