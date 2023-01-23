package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Objects;
import java.util.concurrent.Executor;

import uac.imsp.clockingapp.Controller.control.FingerprintAttendanceController;
import uac.imsp.clockingapp.Controller.util.IFingerprintAttendanceController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IFingerprintAttendanceView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public   class FingerprintAttendance extends AppCompatActivity
		implements IFingerprintAttendanceView
{
	IFingerprintAttendanceController fingerprintAttendancePresenter;

	SharedPreferences preferences;
	boolean useQRcode,dark;
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		retrieveSharedPreferences();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_attendance);
		if(dark)
			setTheme(R.style.DarkTheme);

		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.clocking_text);
		initView();
		fingerprintAttendancePresenter=new FingerprintAttendanceController
				(this);

		BiometricManager biometricManager = BiometricManager.from(this);
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
						//viewEditAndSave();
					}

					@Override
					public void onAuthenticationSucceeded(@NonNull BiometricPrompt.
							AuthenticationResult result) {
						super.onAuthenticationSucceeded(result);
						byte[] data = Objects.requireNonNull(Objects.requireNonNull(result.
								getCryptoObject()).getCipher()).getIV();
						fingerprintAttendancePresenter.onAuthSuccess(data);
					}

					@Override
					public void onAuthenticationFailed() {
						super.onAuthenticationFailed();
						new ToastMessage(FingerprintAttendance.this,
								"Fingerprint not registered on the device," +
										" save it and try aigain");
					}
				});


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

	private void initView() {
		new ToastMessage(this, getString(R.string.clocking_text));

	}

	public void retrieveSharedPreferences(){
		String PREFS_NAME="MyPrefsFile";
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		dark=preferences.getBoolean("dark",false);
		useQRcode=preferences.getBoolean("useQRCode",true);
	}

	@Override
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
	public void onClockInSuccessful() {
		new ToastMessage(this,getString(R.string.enter_pointed));
		onBackPressed();

	}

	@Override
	public void onClockOutSuccessful() {

		new ToastMessage(this,getString(R.string.out_pointed));
		onBackPressed();

	}
}