package uac.imsp.clockingapp.Controller.util;

import java.util.Hashtable;

public interface IConsultStatisticsByServiceController {

    //String formatDate(int year,int month,int day );
    void onConsultStatisticsByService();
    ////void onStartDateSelected(int year,int month,int day);
    //void onEndDateSelected(int year,int month,int day);
    //void onConfirmResult(boolean confirm);

 Hashtable<String, Integer> onReportStatusChanged(int status);
	void onPreviousMonth();

    void onNextMonth();
}

