package uac.imsp.clockingapp.Controller.util;

public interface IChangePasswordController {
    void onSubmit(String oldPassword, String newPassword,String newPasswordConfirm);
    void onStart(int number);
    String md5(String str);
}

