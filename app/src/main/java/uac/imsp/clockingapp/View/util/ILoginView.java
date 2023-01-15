package uac.imsp.clockingapp.View.util;

public interface ILoginView {
    void onLoginSuccess(int number);
    void onSimpleEmployeeLogin();
    void onLoginError(int message);
    void onClocking();
    void onPasswordError(int errorNumber);
    void onUsernameError(int username);
    void onShowHidePassword();
    void onMaxAttempsReached();
    void askWish();
    void onPositiveResponse();
    void onNegativeResponse();

}
