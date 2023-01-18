package uac.imsp.clockingapp.View.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.ClockingInOutController;
import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IClockInOutView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ClockInOut extends AppCompatActivity
        implements View.OnClickListener, IClockInOutView {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    int Number;
    BarcodeDetector barcodeDetector;
    private  CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //Button btnAction;
    String intentData = "";

    IClockInOutController clockInOutPresenter;
    boolean useQRcode;
    SharedPreferences preferences;

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.switch_camera_orientation) {

            //clockInOutPresenter.onSwitchCamara();

        }
        else if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.clocking_text);
        retrieveSharedPreferences();
        setContentView(useQRcode? R.layout.activity_scanner:R.layout.fingerprint_gesture);
        initViews();
        initialiseDetectorsAndSources(true);


        clockInOutPresenter = new ClockingInOutController(this);

    }
    private void initialiseDetectorsAndSources(boolean back) {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)

                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setFacing(back?CameraSource.CAMERA_FACING_BACK:CameraSource.CAMERA_FACING_FRONT )
                .setAutoFocusEnabled(true) //you can use setAutoFocusEnabled(false) if you don't want the camera to autofocus
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ClockInOut.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ClockInOut.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(() -> {
                        intentData = barcodes.valueAt(0).displayValue;
                        stopCameraAndDetector();

                        txtBarcodeValue.setText(intentData);

                        try {
                            Number = Integer.parseInt(intentData);

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            Number = 0;
                        }
                        clockInOutPresenter.onClocking(Number);



                    });
                }
            }
        });
    }

    private void stopCameraAndDetector() {
        try {
            cameraSource.stop();
        } catch (Exception e) {
            // Handle exceptions here
        }

        barcodeDetector.release();
    }



    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);

    }
public void retrieveSharedPreferences(){
        String PREFS_NAME="MyPrefsFile";
    preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);
    useQRcode=preferences.getBoolean("useQRCode",true);
}


    @Override
    protected void onPause() {
        super.onPause();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }
    }
    public void front(){
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();

    }

    public void back(){
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();
    }


    private void switchCamera(){
        boolean toBack;
        cameraSource.stop();
        cameraSource.release();


        toBack= Objects.requireNonNull(cameraSource).getCameraFacing() ==
                CameraSource.CAMERA_FACING_FRONT;


        cameraSource.stop();

              initialiseDetectorsAndSources(toBack);





    }


    public boolean onCreateOptionsMenu(@NonNull android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.camera_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    void startCamera(){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            back();
            cameraSource.start(surfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (intentData.length() > 0) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
        }
    }


    @Override
    public void onLoad() {
       new ToastMessage(this, getString(R.string.clocking_text));

    }

    public void onClockInSuccessful() {
      new ToastMessage(getApplicationContext(),getString(R.string.enter_pointed));
      onBackPressed();

    }

    @Override
    public void onClockOutSuccessful() {

        new ToastMessage(this,getString(R.string.out_pointed));
        onBackPressed();
    }


    public void onClockingError(int errorNumber) {
        switch (errorNumber){
            case 1:
               new ToastMessage(this,getString(R.string.employee_not_found));
               break;
            case 2:
                new ToastMessage(this,getString(R.string.should_not_work_today));
                break;
            case 3:

                new ToastMessage(this,getString(R.string.in_out_already_marked));
                break;
            default:
                break;
        }
        onBackPressed();


    }

    @Override
    public void onSwitchCamera() {

            switchCamera();

    }

}