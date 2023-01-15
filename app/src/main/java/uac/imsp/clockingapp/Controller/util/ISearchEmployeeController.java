package uac.imsp.clockingapp.Controller.util;

public interface ISearchEmployeeController {
    //Take the data with which  employee will be searched
    void onSearch(String data);

    void onEmployeeSelected(int number);
    void onOptionSelected(int which);

    void onStart();



}
