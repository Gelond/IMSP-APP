package uac.imsp.clockingapp.Controller.util;

public interface IConsultPresenceReportController {

    //The controller get the regNumber of the employee concerned
    void onConsultPresenceReport(int number);


    //when he selects the month the controller can compute the presence report
    // void onMonthSelected() ;
     void onPreviousMonth();
     void onNextMonth();
     void onConsultOwnStatistics() ;
     // Hashtable < Hashtable<Integer,String>,Character> onOwnMonthSelected() ;




}

