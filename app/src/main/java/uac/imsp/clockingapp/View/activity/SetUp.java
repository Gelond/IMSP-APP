package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class SetUp extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView imageView;
    Runnable runnable;

    // Create a variable to keep track of the current image
    int currentImage = 1;

    // Declare the delay in seconds
    int delay = 5;

    // Create a handler
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String PREFS_NAME="MyPrefsFile";
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
    }
    public void initView(){
        final Button next=findViewById(R.id.set_up_continue);
        next.setOnClickListener(this);
        imageView=findViewById(R.id.image);

         runnable = new Runnable() {
            @Override
            public void run() {
                // Use a switch statement to switch between the images
                switch (currentImage) {
                    case 1:
                        imageView.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.time);
                        currentImage = 2;
                        break;
                    case 2:
                        imageView.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.boys);
                        currentImage = 3;
                        break;
                    case 3:
                        imageView.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.mobile);
                        currentImage = 4;
                        break;
                    case 4:
                        imageView.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.joy);
                        currentImage = 5;
                        break;
                    case 5:
                        imageView.setImageDrawable(null);
                        imageView.setImageResource(R.drawable.enjoy);
                        currentImage = 1;
                        break;

                    default:
                        break;
                }
                // Post the runnable again with the specified delay
                handler.postDelayed(this, delay * 1000L);
            }
        };

// Start the runnable
        handler.postDelayed(runnable, delay * 1000L);


    }


@Override
protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Services.class);
        finish();
startActivity(intent);
editor.putString("nextStep","service");
editor.apply();
    }
}
