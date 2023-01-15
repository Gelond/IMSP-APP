package uac.imsp.clockingapp.View.util;

public interface IConsultPresenceReportView {

    void onStart(int firstDayNumber, int lastDayNameNumber, int mouthLength, int month, int year);
    void onMonthSelected(String[] report, int fitstDayNumber);
    void onReportNotAccessible(boolean nextMonthReport);
    void onReportAccessible(boolean nextMonthReport);



}


