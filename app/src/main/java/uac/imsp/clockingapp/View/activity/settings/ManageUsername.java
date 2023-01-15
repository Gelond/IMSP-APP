package uac.imsp.clockingapp.View.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import uac.imsp.clockingapp.R;

public class ManageUsername extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
CompoundButton.OnCheckedChangeListener {

    RadioButton emailAsUsername, editUsername;
    RadioGroup usernameGroup;
    SwitchMaterial showPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean GenPwd, EditUsername, EditPassword, Add, Update, Delete;
    CheckBox add, update, delete, generatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_username);
        retrieveSharedPreferences();
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();

    }

    public void initView() {
        //radioGroup=findViewById(R.id.password_group);
        emailAsUsername = findViewById(R.id.use_email);
        generatePassword = findViewById(R.id.generate_password);
        editUsername = findViewById(R.id.edit_username);
        showPassword = findViewById(R.id.switch_show_password);
        usernameGroup = findViewById(R.id.username_group);
        add = findViewById(R.id.setting_add);
        update = findViewById(R.id.setting_update);
        delete = findViewById(R.id.setting_delete);


        if (!GenPwd)
            showPassword.setVisibility(View.GONE);
        generatePassword.setChecked(GenPwd);
        editUsername.setChecked(EditUsername);
        emailAsUsername.setChecked(!EditUsername);
        showPassword.setChecked(EditPassword);
        add.setChecked(Add);
        update.setChecked(Update);
        delete.setChecked(Delete);
        usernameGroup.setOnCheckedChangeListener(this);
        // radioGroup.setOnCheckedChangeListener(this);
        showPassword.setOnCheckedChangeListener(this);
        add.setOnCheckedChangeListener(this);
        generatePassword.setOnCheckedChangeListener(this);

    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void retrieveSharedPreferences() {
        final String PREFS_NAME = "MyPrefsFile";
        preferences = getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preferences.edit();
        //EmailAsUsername=preferences.getBoolean("emailAsUsername",false);

        GenPwd = preferences.getBoolean("generatePassword", false);
        EditUsername = preferences.getBoolean("editUsername", true);
        EditPassword = preferences.getBoolean("showPasswordDuringAdd", true);
        Add = preferences.getBoolean("notifyAdd", true);
        Delete = preferences.getBoolean("notifyDelete", false);
        Update = preferences.getBoolean("notifyUpdate", false);

    }

    @Override
    public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
        if (group.getId() == R.id.username_group) {

            if (checkedId == R.id.use_email) {
                editor.putBoolean("editUsername", false);
            } else if (checkedId == R.id.edit_username) {
                editor.putBoolean("editUsername", true);
            }
            editor.apply();
        }
    }

    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_show_password)
            editor.putBoolean("showPasswordDuringAdd", isChecked);

        else if (buttonView.getId() == R.id.setting_add)
            editor.putBoolean("notifyAdd", isChecked);
        else if (buttonView.getId() == R.id.setting_update)
            editor.putBoolean("notifyUpdate", isChecked);
        else if (buttonView.getId() == R.id.setting_delete)
            editor.putBoolean("notifyDelete", isChecked);
        else if (buttonView.getId() == R.id.generate_password) {
            editor.putBoolean("generatePassword", isChecked);
            showPassword.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }

        editor.apply();
    }

}