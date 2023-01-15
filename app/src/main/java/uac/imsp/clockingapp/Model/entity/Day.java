package entity;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;

/** Represents a day.
        *<br/>
        * Proposes day attributes and exposes  methods to manage easily days.<br/>
        *
        * Day has methods to get date, month, year and other properties of a day and
        * allow us to perform easily some operations on dates
        * @author Ezéchiel G. ADEDE
        * @version 1.0
        * @since 2023

        */
public class Day  implements IDay {


    /**Represents the id of the day **/
    private int Id;
    /**Represents the month of the day with the format MM **/
    private String FormatedMonth,
    /**Represents the year of the day with the format YYYY **/
            FormatedYear,
    /**Represents the day with the format dd **/
            FormatedDay,
    /**Represents the date with the format dd**/
            Date;
           /** A constructor of Day: it creates the current day*/
            public Day (){Date =java.time.LocalDate.now().toString();
        setFormat();


    }
    /**  Sets aformated year, formated month and formated day to the Day*/
    public void setFormat(){
        String []  date =Date.split("-");
        FormatedYear=date[0];
        FormatedMonth=date[1];
        FormatedDay=date[2];

    }
    /** A constructor of Day: it creates a day with the given date
     * @param date is the date with the format YYYY-MM-dd*/
    public Day(String date ) {

     this.Date=date;
     setFormat();
    }
    /** A constructor of Day: it creates a new day with the given year, month and day number
     * @param year is the year
     * @param month is the month
     * @param day is the day number in month*/
    public Day(int year,int month,int day){

        FormatedYear=""+year;
        FormatedDay="";
        FormatedMonth="";
        if(month<10)
            FormatedMonth="0";
        if(day <10)
            FormatedDay="0";
        FormatedDay+=day;
        FormatedMonth+=month;
        this.Date=FormatedYear+"-"+FormatedMonth+"-"+FormatedDay;

    }

    /** A constructor of Day: it creates a new day with the given  month and day number
     * the current year is used
     * @param month is the month
     * @param day is the day number in month*/
    public Day(int month,int day){

        this( Calendar.getInstance().get(Calendar.YEAR) ,month,day);
    }
    /**@return  the date of a day**/
    public  String getDate(){
        return Date;
    }

    public  void setDate(String date){
        Date=date;
    }
    /**@return  id of a day**/
    public int getId(){
        return Id;
    }
    /**@return  the month of a day**/
    public int getMonth()  {
        return  Integer.parseInt(FormatedMonth) ;

    }

    /**@return  the year of a day**/
    public int getYear() {

return Integer.parseInt(FormatedYear);

    }
    /**@return  the day plus a month, meaning the matching day with
     *  the same number  in the next month**/
    public  Day addMonth(){
        Day day;
        if(this.getMonth()==12)
            day=new Day(this.getYear()+1,1,1);
        else
            day=new Day(this.getYear(),this.getMonth()+1,1);
        return day;
    }
    /**@return  the day minus a day, meaning the next day*/
    public  Day addADay(){
        int year=getYear(),month=getMonth(),d=getDayOfMonth();
        Day day;
        if(getDayOfMonth()==getLenthOfMonth()) {

             if(getMonth()==12){
                year++;
                month=1;

            }
            else {
                month++;
            }
            d=1;
        }
        else
            d++;

        day=new Day(year,month,d);
        return day;
    }

    /**@return  the day minus a day, meaning the previous day**/
    public  Day subtractADay(){
        int year=getYear(),month=getMonth(),d=getDayOfMonth();
        Day day;
        if(getDayOfMonth()==1) {

            if(getMonth()==1){
                year--;
                month=12;

            }
            else {
                month--;
            }
            day=new Day(year,month,1);
            d=day.getLenthOfMonth();
        }
        else
            d--;

        day=new Day(year,month,d);
        return day;
    }

    /**@return  the day minus a month, meaning the matching day with
     *  the same number  in the previous month**/
    public  Day subtractMonth(){
        Day day;
        if(this.getMonth()==1)
            day=new Day(this.getYear()-11,12,1);
        else
            day=new Day(this.getYear(),this.getMonth()-1,1);
        return day;
    }
    /**@return  the month  of a day as a string**/
    public String getMonthPart(){
        return this.getDate().split("-")[0]+"-"+this.getDate().split("-")[1];
    }
    /**@return  the day of week : it ranges from 1 to 7**/
    public int getDayOfWeek()  {
     int dayOfWeek;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = this.Date;
        java.util.Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        if(dayOfWeek==0)
            dayOfWeek=7;
        return dayOfWeek ;
    }
    /**@return  the day of the month of the day**/
    public int getDayOfMonth()  {
        return Integer.parseInt(FormatedDay) ;


    }
    /**@return  the first day of the month that contains the day**/
    public int  getFirstDayOfMonth()  {
        //wether it is monday, tuesday , etc


        Day d = new Day(this.getYear(),this.getMonth(),1);

        return  d.getDayOfWeek();
    }
    /**@return  the day of the length of the month that contains the day
     *, that's to say the number of days in this month**/
    public int getLenthOfMonth()  {
        YearMonth yearMonth=YearMonth.of(getYear(),getMonth());
        return yearMonth.lengthOfMonth();
    }
    /**Checks if a day is weekend or not
     * @return  true if the day is weekend and false otherwise**/
    public boolean isWeekEnd()  {
        return (getDayOfWeek()==6 || getDayOfWeek()==7);
    }
    /**@return  the french format of  the day meaning dd/MM/YYYY**/
    public String getFrenchFormat()  {
        return FormatedDay+
                "/"+FormatedMonth+
                "/"+FormatedYear;

    }

 /** Sets id to Day
  * @param id: the current attendance status of the employee**/
    public void setId(int id) {
        Id = id;

    }

    /**@return  year that contains the day as a string**/
    public String getFormatedYear() {
        return FormatedYear;
    }
    /**@return  month that contains the day as a string**/
    public String getFormatedMonth() {
        return FormatedMonth;
    }
    /**@return the number of the day in its month as a string**/
    public String getFormatedDay() {
        return FormatedDay;
    }

}