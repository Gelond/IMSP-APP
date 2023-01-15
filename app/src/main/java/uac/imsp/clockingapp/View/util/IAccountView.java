package uac.imsp.clockingapp.View.util;

public interface IAccountView {
    void onSuccess();
    void onWrongPassword();
    void onStart(String username);

}
