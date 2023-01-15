package uac.imsp.clockingapp.Controller.util;

import java.text.ParseException;
import java.util.Hashtable;

public interface IDeleteEmployeeController {
    void onLoad(int number, Hashtable<String, Object> informations) throws ParseException;






    void onDeleteEmployee(int adminNumber);
    void onConfirmResult(boolean confirmed);

}
