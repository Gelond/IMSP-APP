package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import androidx.annotation.NonNull;

import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.View.util.IClockInOutView;

public class ClockingInOutController extends ClockingController
        implements IClockInOutController
         {


    public ClockingInOutController(@NonNull IClockInOutView clockInOutView) {
        super(clockInOutView);
    }

    public ClockingInOutController(@NonNull IClockInOutView clockInOutView,
                                   Context context) {

        super(clockInOutView,context);
        //clockInOutView.onLoad();

    }

    //used for android unit test



    public void onClocking(int number) {
        super.onClocking(number);

        }

             @Override
             public void clock(int number, String date, String time) {
                 super.clock(number, date, time);
             }

             @Override
    public void onSwitchCamara() {
    clockInOutView.onSwitchCamera();
    }

}
