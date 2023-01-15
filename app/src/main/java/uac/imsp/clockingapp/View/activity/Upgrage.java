package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Upgrage extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upgrage);
		new ToastMessage(this,"Mise à jour efféctuée avec succès");
	}
}