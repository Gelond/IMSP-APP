package uac.imsp.clockingapp.View.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastMessage  extends Toast {

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastMessage(Context context,String message) {
        super(context);
        setGravity(Gravity.CENTER_VERTICAL,0,0);

        makeText(context,message,LENGTH_SHORT).show();

    }
}
