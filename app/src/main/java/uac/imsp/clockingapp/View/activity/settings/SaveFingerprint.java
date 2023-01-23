package uac.imsp.clockingapp.View.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Objects;
import java.util.concurrent.Executor;

import uac.imsp.clockingapp.Controller.control.settings.SaveFingerprintController;
import uac.imsp.clockingapp.Controller.util.settings.ISaveFingerprintController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;
import uac.imsp.clockingapp.View.util.settings.ISaveFingerprintView;

public class SaveFingerprint extends AppCompatActivity implements ISaveFingerprintView {
    BiometricManager biometricManager ;
    ISaveFingerprintController saveFingerprintPresenter;
    boolean dark;

    private void retrieveSharedPreferences() {
        String PREFS_NAME="MyPrefsFile";
        SharedPreferences preferences= getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        dark=preferences.getBoolean("dark",false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        retrieveSharedPreferences();
        if(dark)
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savefingerprint);
        saveFingerprintPresenter=new SaveFingerprintController(this);

        // calling the action bar
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.save_fingerprint);


         biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager
                .Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators
                .DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                //b.biometricCardView.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                //b.biometricCardView.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    final Intent enrollIntent;
                    enrollIntent = new Intent
                            (Settings.ACTION_BIOMETRIC_ENROLL);
                    enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_WEAK |
                                    BiometricManager.Authenticators.DEVICE_CREDENTIAL);
                    startActivityForResult(enrollIntent, IntentIntegrator.REQUEST_CODE);
                }
                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
            default:
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence
                    errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.
                    AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                byte[] fingerprintData = Objects.requireNonNull(result.getCryptoObject()).getCipher().getIV();

                int currentUser = getIntent().getIntExtra
                        ("CURRENT_USER", 0);

                saveFingerprintPresenter.onFingerprintEnrollement(currentUser,fingerprintData);

               /* FingerprintManager fingerprintManager = (FingerprintManager)
                        getSystemService(FINGERPRINT_SERVICE);*/


// Compare the stored fingerprint data with the live capture data
                /*fingerprintManager.authenticate(
                        new FingerprintManager.CryptoObject(cipher), null,
                        0, new
                        FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.
                                                                  AuthenticationResult
                                                                  result) {

                        // Live capture data matches the stored fingerprint data

                        new ToastMessage(SaveFingerprint.this,"Super");
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        // Live capture data does not match the stored fingerprint data

                        new ToastMessage(SaveFingerprint.this,"djf");
                    }
                },
                        null);*/ {

                }



            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

       /* BIOMETRIC_STRONG] Authentication allows fingerprint sensor only.
[BIOMETRIC_WEAK] Authentication allows all biometrics i.e. fingerprint, face, and iris.
[DEVICE_CREDENTIAL] Authentication using a screen lock credential – the user's PIN, pattern, or password.*/

        BiometricPrompt.PromptInfo promptInfo = new
                BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.place_your_finger))
                .setSubtitle(getString(R.string.place_your_finger))
                .setDescription(getString(R.string.fingerprint_description))
                .setAllowedAuthenticators(BiometricManager.Authenticators.
                        BIOMETRIC_STRONG
                )
                .setNegativeButtonText(getString(R.string.cancel))
                .setConfirmationRequired(false)
                .build();
        biometricPrompt.authenticate(promptInfo);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess() {
        new ToastMessage(this, "Fingerprint succcessfully registered");


    }
}
