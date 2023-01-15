package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsByEmployeeView {

    void onStart(int firstDayNumber, int lastDayNameNumber, int mouthLength, int month, int year);
    void onMonthSelected(Hashtable<Character, Float> statistics);
    void onReportNotAccessible(boolean nextMonthReport);
    void onReportAccessible(boolean nextMonthReport);


}


