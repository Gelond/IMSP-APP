package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsByServiceView {

        //Message to print,tile of message
    //void onConsultStatistics(String title,String message);
    //Case of no service found
    void onNoServiceFound();
    void onServiceFound(Hashtable<String, Integer> rowSet);
    void onStart(int firstDayNumber, int lastDayNumber, int mouthLength, int month, int year);
    void onReportNotAccessible(boolean nextMonthReport);
    void onReportAccessible(boolean nextMonthReport);
    void onReportStatusChanged(int status);

}



