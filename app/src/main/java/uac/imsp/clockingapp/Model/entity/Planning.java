package entity;

import java.util.Hashtable;
/** Represents a planning.
 *<br/>
 * Allow us manipulate easily plannings
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class Planning implements IPlanning {
    /**Represents the id of the planning **/
    private int Id;
    /**Represents the start time **/
    private final String StartTime;
    /**Represents the end time **/
    private final String EndTime;
    /**Represents the workdays **/
    private byte[] WorkDays;


    /** A constructor of planning: it creates a new planning
     *  with the given  start time, end time and work days
     * @param startTime is the time to start working
     * @param endTime is the time to end working
     * @param workDays represents the work days in the week*/
    public Planning(String startTime,String endTime,byte[] workDays){
        StartTime=startTime;
        EndTime=endTime;
        WorkDays=workDays;
    }
    /**@return  the id of the planning**/
    public  int getId(){
        return Id;
   }
    /**@return  the start time of the planning**/
    public String getStartTime(){
        return StartTime;
    }
    /**@return  the end time of the planning**/
    public String getEndTime(){
        return EndTime;
    }
    /**@return  the work days of the planning**/
    public byte[] getWorkDays() {
        return WorkDays;
    }
    /**Sets work days to the planning
     * @param workDays: the work days**/
    public void setWorkDays(byte[] workDays) {
            WorkDays = workDays;
    }
    /**Sets id to the planning
     * @param id: the planning id**/
    public void setId(int id){
        Id=id;
    }
    /**@return  the start and end time of the planning **/
    public Hashtable<String,String> extractHours(){
        Hashtable<String,String> h = new Hashtable<>();

        h.put("start",StartTime.split(":")[0]);
        h.put("end",EndTime.split(":")[0]);
        return h;

    }



}