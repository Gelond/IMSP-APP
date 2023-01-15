package uac.imsp.clockingapp.View.util;

public interface IUpdateEmployeeView {
    void onSomethingchanged();
    void onNothingChanged();
    void onUpdateEmployeeError(int errorNumber);
    void askConfirmUpdate();


}


