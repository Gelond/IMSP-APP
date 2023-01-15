package uac.imsp.clockingapp.View.util;


public interface IRegisterEmployeeView {
    void onRegisterEmployeeSuccess();
    void onRegisterEmployeeError(int errorNumber);
    void onShowHidePassword(int viewId,int eyeId);
    void sendEmail(String [] to,String qrCodeFileName,
                   String lastname, String firstname,
                   String username, String password,String gender
                   );

}

