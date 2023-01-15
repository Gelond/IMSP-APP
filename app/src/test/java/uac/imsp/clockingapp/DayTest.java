package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import entity.Day;


public class DayTest {
    @Test
    public void  test(){
        Day day=new Day("1970-01-01");
        assertNotNull(day);
        assertEquals("1970-01-01",day.getDate());
        assertEquals("1970",day.getFormatedYear());
        assertEquals("01",day.getFormatedMonth());
        assertEquals("01",day.getFormatedDay());
        assertEquals(1970,day.getYear());
        assertEquals(1,day.getDayOfMonth());
        assertEquals(1,day.getMonth());
        assertEquals("01/01/1970",day.getFrenchFormat());

    }
    @Test
    public void testDayWithCurrentDate(){
        Day now = new Day();
        assertNotNull(now);
        //consider the current date
        assertEquals("2022-11-14",now.getDate());
        assertEquals("2022",now.getFormatedYear());
        assertEquals("11",now.getFormatedMonth());
        assertEquals("14",now.getFormatedDay());
        assertEquals(2022,now.getYear());
        assertEquals(14,now.getDayOfMonth());
        assertEquals(11,now.getMonth());
        assertEquals (1,now.getDayOfWeek());
        assertFalse(now.isWeekEnd());

    }
    @Test
    public void runtest(){
        Day day=new Day(2022,11,18);
        int n= day.getFirstDayOfMonth();
        assertEquals(2,n);
        day=new Day();
        n=day.getFirstDayOfMonth();
        assertEquals(4,n);

    }
    @Test
    public void assureMonthLengthIsCorrect(){
        Day day=new Day(2022,11,18);
        assertEquals(30,day.getLenthOfMonth());
        day=new Day(2022,12,3);
        assertEquals(31,day.getLenthOfMonth());
    }
@Test
    public void getOfWeek()  {
Day d= new Day();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = d.getDate();
        assertEquals("2022-11-14",dateInString);
        java.util.Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();

        assert date != null;
        calendar.setTime(date);
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        if(dayOfWeek==0)
            dayOfWeek=7;
        assertEquals(1,dayOfWeek);
    }
}


