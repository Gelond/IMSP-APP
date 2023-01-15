package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dao.EmployeeManager;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IChangePasswordController;
import uac.imsp.clockingapp.View.util.IAccountView;

public class ChangePasswordController implements IChangePasswordController {

private final IAccountView changePasswordView;
private Employee employee;
  private EmployeeManager employeeManager;
  private Context context;
  private String oldPasssword;
    public ChangePasswordController(IAccountView changePasswordView){
    this.changePasswordView=changePasswordView;

    this.context= (Context) this.changePasswordView;
    }
    public ChangePasswordController(IAccountView changePasswordView, Context context){
        this.changePasswordView=changePasswordView;
        this.context=context;
    }



   public void onStart(int number) {
       employee = new Employee(number);
          employeeManager = new EmployeeManager(context);
          employeeManager.open();
           employeeManager.setAccount(employee);
           employeeManager.close();
           //The view gets the username from the controller
           changePasswordView.onStart(employee.getUsername());
          // oldPassword=

       }
public String md5(String password) {
        MessageDigest digest;
        byte[] messageDigest;
        StringBuilder hexString;
        try{
            digest=java.security.MessageDigest.getInstance("MD5");
            messageDigest=digest.digest();
            hexString=new StringBuilder();
            for (byte element:messageDigest) {
                hexString.append(Integer.toHexString(0xFF & element));
                return hexString.toString();

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String getOldPasssword() {
        return oldPasssword;
    }

    @Override
    public void onSubmit(String oldPasswordEdited, String newPassword,String newPasswordConfirm) {
        try {
             if(TextUtils.isEmpty(oldPasswordEdited) ||
                     TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(newPasswordConfirm)
             )
                 changePasswordView.onWrongPassword();

           else if (!oldPasswordEdited.matches("\\w{6,}") ||
                     !newPassword.matches("\\w{6,}"))


                changePasswordView.onWrongPassword();
            else if (!oldPasswordEdited.equals((md5(employee.getPassword()))))
                changePasswordView.onWrongPassword();

            else if(!newPasswordConfirm.equals(newPassword))

                changePasswordView.onWrongPassword();
            else {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        employeeManager=new EmployeeManager(context);
                        employeeManager.open();
                        employeeManager.changePassword(employee, (newPassword));
                        employeeManager.close();
                    }
                };
                 AsyncTask.execute(runnable);
                changePasswordView.onSuccess();
            }
        }

        catch(NullPointerException e ){
            changePasswordView.onWrongPassword();
            }




    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}