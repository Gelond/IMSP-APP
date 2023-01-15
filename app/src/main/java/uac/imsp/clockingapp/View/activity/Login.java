package uac.imsp.clockingapp.View.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.LoginController;
import uac.imsp.clockingapp.Controller.util.ILoginController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ILoginView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Login extends AppCompatActivity
                   implements View.OnClickListener , TextWatcher,
        ILoginView {
    private ImageView eye;
    private int Number;
    private  EditText Username,Password;
    ILoginController loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.login_button_text));
        initView();
        loginPresenter = new LoginController(this);


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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity((new Intent(this, StartScreen.class)));
    }

    @Override
    public void onClick(@NonNull View v) {
        if(v.getId()==R.id.login_button)
            loginPresenter.onLogin(Username.getText().toString(),
                Password.getText().toString());
        else if(v.getId()==R.id.login_show_password)
            loginPresenter.onShowHidePassword();
    }

    @Override
    public void onLoginSuccess(int number) {
        Number=number;



    }

    @Override
    public void onSimpleEmployeeLogin() {
simpleLogin();
new ToastMessage(this,getString(R.string.simple_login));
    }

    @Override

    public void onLoginError(int errorNumber) {
        switch (errorNumber)
        {
            case 0:
            new ToastMessage(this,getString(R.string.username_required));
            break;
            case 1:
                new ToastMessage(this,getString(R.string.username_invalid));
                break;
            case 2:
                new ToastMessage(this,getString(R.string.password_required));
                break;
            case 3:
                new ToastMessage(this,getString(R.string.password_invalid));
                break;
            case 4:
                new ToastMessage(this,getString(R.string.user_or_password));
                break;
            default:
                break;
        }


    }

    @Override
    public void onClocking() {

    startActivity(new Intent(Login.this,ClockInOut.class));

    }

    @Override
    public void onPasswordError(int errorNumber) {
        switch (errorNumber){
            case 0:
                Password.setError(getString(R.string.required));
                break;
            case 1:
                Password.setError(getString(R.string.at_least));
                break;
            default:
                break;
        }


    }

    @Override
    public void onUsernameError(int errorNumber) {
        switch (errorNumber){
            case 0:
                Username.setError(getString(R.string.required));
                break;
            case 1:
                Username.setError(getString(R.string.at_least));
                break;
            case 2:
                Username.setError(getString(R.string.at_most));
                break;
            default:
                break;


        }


    }

    @Override
    public void onShowHidePassword() {
            if(Password.getTransformationMethod().
                    equals(PasswordTransformationMethod.getInstance()))
            {
                eye.setImageResource(R.drawable.ic_baseline_visibility_off_18);


                //show password
                Password.setTransformationMethod(HideReturnsTransformationMethod.
                        getInstance());
            }
            else{
                eye.setImageResource(R.drawable.baseline_visibility_black_18);


                //hide password
                Password.setTransformationMethod(PasswordTransformationMethod.
                        getInstance());
            }


        }

    @Override
    public void onMaxAttempsReached() {
         new ToastMessage(this,getString(R.string.max_reached));


        finish();
        startActivity(new Intent(Login.this,StartScreen.class));
    }

    @Override
    public void askWish() {
        String pos=getString(R.string.yes),neg=getString(R.string.no),
        title=getString(R.string.confirm_login_title),
        message=getString(R.string.confirm_login);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which)
                        -> loginPresenter.onConfirmResult(true))

                .setNegativeButton(neg, (dialog, which)
                        ->
                        loginPresenter.onConfirmResult(false));

        AlertDialog alert = builder.create();
        alert.setTitle(title);

        alert.show();

    }

    @Override
    public void onPositiveResponse() {
       String message=getString(R.string.notify_admin_login);
        Intent intent = new Intent(this, GeneralMenu.class);
        //finish();
        intent.putExtra("CURRENT_USER",Number);
        new ToastMessage(this,message);

        startActivity(intent);
        Username.getText().clear();
        Password.getText().clear();
          }

    @Override
    public void onNegativeResponse() {
        Intent intent=new Intent(this,SimpleEmployeeMenu.class);
        intent.putExtra("CURRENT_USER",Number);
        new ToastMessage(this,getString(R.string.notify_simple_login));
        startActivity(intent);


    }
    public void simpleLogin(){
        startActivity(new Intent(this,SimpleEmployeeMenu.class));

    }


    public void initView(){

        Username=findViewById(R.id.login_username);
        Password=findViewById((R.id.login_password));
        eye=findViewById(R.id.login_show_password);
        eye.setOnClickListener(this);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);
        Username.addTextChangedListener(this);
        Password.addTextChangedListener(this);

        Password.setOnClickListener(this);



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(@NonNull Editable s) {
        if(Username.getText().toString().equals(s.toString()))
            loginPresenter.onUsernameEdit(s.toString());
        else if(Password.getText().toString().equals(s.toString()))

            loginPresenter.onPasswordEdit(s.toString());


    }
}
