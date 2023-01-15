package uac.imsp.clockingapp.Controller.control;

import static dao.EmployeeManager.CAN_NOT_LOGIN;
import static entity.Employee.EMPTY_PASSWORD;
import static entity.Employee.EMPTY_USERNAME;
import static entity.Employee.INVALID_PASSWORD;
import static entity.Employee.INVALID_USERNAME;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.ILoginController;
import uac.imsp.clockingapp.View.util.ILoginView;

public class LoginController  implements ILoginController {
    //public final static String type = null;
    public static  int CurrentEmployee;
    private  int attempNumber=0;
    private  final Context context;
    //shouldn't be final
    private EmployeeManager employeeManager;
    Runnable runnable;

    ILoginView loginView;
    public LoginController(ILoginView loginView) {

        this.loginView = loginView;
         context = (Context) this.loginView;

    }





    @Override
    public void onLogin(String username, String password) {
      int loginCode;
        employeeManager=new EmployeeManager(context);
        employeeManager.open();
      username="User10";
      password="Aab10%";

       // Construction d'un employé voulant se connecter
        Employee employee = new Employee(username,password);
       // employee=new Employee("User10","Aab10%");


         loginCode=employee.validUser();

        if(loginCode==EMPTY_USERNAME)
            loginView.onLoginError(0);
        else if(loginCode==INVALID_USERNAME)
            loginView.onLoginError(1);
        else if(loginCode==EMPTY_PASSWORD)
            loginView.onLoginError(2);
        else if(loginCode==INVALID_PASSWORD)
            loginView.onLoginError(3);
        else {

            employeeManager = new EmployeeManager(context);
            employeeManager.open();

            loginCode = employeeManager.connectUser(employee);

            employeeManager.close();


            if (loginCode == CAN_NOT_LOGIN) {
                //loginView.onLoginError("Username ou mot de passe incorrect !");
                loginView.onLoginError(4);
                attempNumber++;
                if (attempNumber == 3)
                    //loginView.onMaxAttempsReached("Trois tentatives d'authentification échouées !");
                    loginView.onMaxAttempsReached();


            } else {
                CurrentEmployee = employee.getRegistrationNumber();
                loginView.onLoginSuccess(CurrentEmployee);

                if (employee.isAdmin())
                    loginView.askWish(
                    );
                else
                    loginView.onSimpleEmployeeLogin();


                loginView.askWish();


            }
        }}
    @Override
    public void onClocking() {
        loginView.onClocking();

    }

    @Override
    public void onUsernameEdit(@NonNull String username) {

        String uname=username.trim();
        if(TextUtils.isEmpty(uname))
            loginView.onUsernameError(0);
        else if(uname.length()<6)
            loginView.onUsernameError(1);
        else if(uname.length()>30)
            loginView.onUsernameError(2);
    }

    @Override
    public void onPasswordEdit(@NonNull String password) {
        String pwd=password.trim();

        if(TextUtils.isEmpty(pwd))
            loginView.onPasswordError(0);
        else if(pwd.length()<6)
            loginView.onPasswordError(1);


    }

    @Override
    public void onShowHidePassword() {


        loginView.onShowHidePassword();

    }
    public void updateAttendance(Employee employee){
        runnable= () -> {
            String statut;
            employeeManager=new EmployeeManager(context);
            employeeManager.open();
            if(employeeManager.shouldNotWorkToday(employee))
            {
                if(new Day().isWeekEnd())
                    statut="Week-end";


                else
                    statut="Hors service";

            }
            else
                statut="Absent";
            employeeManager.updateCurrentAttendance(employee,statut);
            employeeManager.close();


        };
        AsyncTask.execute(runnable);



    }

    @Override
    public void onConfirmResult(boolean confirmed) {
        if (confirmed) {
            runnable= () -> {
                employeeManager=new EmployeeManager(context);
                employeeManager.open();
                Employee[] employees =employeeManager.search("*");
                employeeManager.close();
                for(Employee employee:employees)
                    updateAttendance(employee);

            };
            AsyncTask.execute(runnable);
            loginView.onPositiveResponse();
        }
        else

            loginView.onNegativeResponse();

    }



}

