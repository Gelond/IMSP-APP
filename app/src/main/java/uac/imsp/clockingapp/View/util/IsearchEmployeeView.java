package uac.imsp.clockingapp.View.util;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.util.Result;

public interface IsearchEmployeeView {



    void onNoEmployeeFound();
    void onEmployeeFound(ArrayList<Result> list);


    void onEmployeeSelected();
    void onUpdate();
    void onDelete();
    void onPresenceReport();
    void onStatistics();
//    void onStart();


}
