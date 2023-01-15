package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.settings.others.LanguagesController;
import uac.imsp.clockingapp.Controller.util.settings.others.ILanguagesController;
import uac.imsp.clockingapp.LocalHelper;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.others.ILanguagesView;

public class Languages extends AppCompatActivity implements ILanguagesView,
		RadioGroup.OnCheckedChangeListener {
	RadioGroup radioGroup;
	RadioButton en,fr;
	String currentLanguage;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	ILanguagesController languagesPresenter;

	int cpt=0;

	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_languages);
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.languages);
		languagesPresenter=new LanguagesController(this);
		retrievePreferences();
		initView();
		if(Objects.equals(currentLanguage, "fr"))
			fr.setChecked(true);
		else if(Objects.equals(currentLanguage, "en"))
			en.setChecked(true);

	}
	public void initView(){
		radioGroup=findViewById(R.id.languages);
		fr=findViewById(R.id.languages_fr);
		en=findViewById(R.id.languages_eng);
		radioGroup.setOnCheckedChangeListener(this);
	}
	public void retrievePreferences(){
		/*final  String PREFS_NAME="MyPrefsFile";
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		editor=preferences.edit();*/

		//currentLanguage=preferences.getString("lang","fr");
		currentLanguage=LocalHelper.getSelectedLanguage(this);
	}

	@Override
	public  void  onLanguageChanged(String lang) {


		LocalHelper.changeAppLanguage(Languages.this,lang);
		if(cpt!=0)
restartApp();
		cpt++;
	}
	public void restartApp() {
		PackageManager pm = getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(getPackageName());
		finishAffinity(); // Finishes all activities.
		startActivity(intent);    // Start the launch activity
		overridePendingTransition(0, 0);
	}

	@Override
	public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
		String lang = null;

		if(group.getId()==R.id.languages)
		{
			if(checkedId==R.id.languages_fr)
				lang="fr";

			else if(checkedId==R.id.languages_eng)
			lang="en";
			languagesPresenter.onLanguageChanged(lang);


		}


	}
}