package uac.imsp.clockingapp.Controller.util;

public interface ILoginController {
    void onLogin(String Username,String Password);
   // void onLoad(int savedVersionCode,  int currentVersionCode);
    void onClocking();
    void onUsernameEdit(String username);
    void onPasswordEdit(String password);
    void onShowHidePassword();
    void onConfirmResult(boolean confirmed);

}
