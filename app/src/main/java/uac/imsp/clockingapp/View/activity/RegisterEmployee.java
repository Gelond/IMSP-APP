package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import uac.imsp.clockingapp.Controller.control.RegisterEmployeeController;
import uac.imsp.clockingapp.Controller.util.IRegisterEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;


public class RegisterEmployee extends AppCompatActivity
        implements NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        IRegisterEmployeeView,
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener ,

        AdapterView.OnItemSelectedListener,
        TextWatcher

{
    IRegisterEmployeeController registerEmployeePresenter;

    private EditText Lastname, Firstname, Email, Username,
            Password, PasswordConfirm,Number, Birthdate;
    private TextView Programm;
    private String Birth;
    DatePickerDialog picker;
    private CircleImageView circlePicture;
    private byte[] Picture;
    private String gend;
    private String SelectedService,SelectedType;
    private int Start=8,End=17;

    boolean editUsername,generatePassword,notice,showPassword;
     CheckBox monday,tuesday,wednesday,thursday,friday,satursday,sunday;
     //private Button workManager;

    CheckBox[] myTable=new CheckBox[7];
    byte[] days=new byte[7];




    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.general_menu_register);

        initView();
        retrieveSharedPreferences();
        if(!editUsername) {
            Username.setFocusable(false);
            Username.setLongClickable(false);
            Email.addTextChangedListener(this);
        }

        if(generatePassword)
        {

            String generetedPassword=generatePassword();
            Password.setText(generetedPassword);
            Password.setFocusable(false);
            Password.setLongClickable(false);
            PasswordConfirm.setText(generetedPassword);
            PasswordConfirm.setFocusable(false);
            PasswordConfirm.setLongClickable(false);
            if(!showPassword){
                findViewById(R.id.password_textview).setVisibility(View.GONE);
                findViewById(R.id.password_confirm_textview).setVisibility(View.GONE);
               findViewById(R.id.password_layout).setVisibility(View.GONE);
                findViewById(R.id.password_confirm_layout).setVisibility(View.GONE);
            }

        }


        gend="M";



            }


    @NonNull
    public  String shuffle(@NonNull String input){
        List<Character> characters = new ArrayList<>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();

    }

    @NonNull
    public  String generateTwoChars(@NonNull String str){
        Random r = new Random();
        String  res=String.valueOf(str.charAt(r.nextInt(str.length())));
        res+=String.valueOf(str.charAt(r.nextInt(str.length())));
        return shuffle(res);
    }
    public  String generatePassword (){
        String upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower=upper.toLowerCase();
        String digits="0123456789";
        String specialChars ="&'(-)=}]@^`[#~%*+-:;,?.§";
        return generateTwoChars(upper)+ generateTwoChars(lower)+ generateTwoChars(digits)+ generateTwoChars(specialChars);

    }



    public void retrieveSharedPreferences(){
        final  String PREFS_NAME="MyPrefsFile";
        SharedPreferences preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editUsername=preferences.getBoolean("editUsername",true);
        generatePassword=preferences.getBoolean("generatePassword",false);
        notice=preferences.getBoolean("notifyDuringAdd",true);
      showPassword=  preferences.getBoolean("showPasswordDuringAdd",true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void imageChooser() {
        Intent intent = new Intent();
        //to specify any kind of image
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            Picture = getBytesFromBitmap(selectedImageBitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        circlePicture.setImageBitmap(selectedImageBitmap);

                    }
                }

            });

    @SuppressLint("DefaultLocale")
        @Override
    public void onClick(@NonNull View v) {
        String username;


  if(v.getId()==R.id.register_show_password)
      registerEmployeePresenter.onShowHidePassword(Password.getId(),v.getId());
  else if(v.getId()==R.id.register_show_password_confirm)
      registerEmployeePresenter.onShowHidePassword(PasswordConfirm.getId(),v.getId());

     else     if (v.getId() == R.id.register_picture_button)
            imageChooser();
        else if (v.getId() == R.id.register_button ) {
          days= workdays();
          username=toString(Username);

            registerEmployeePresenter.onRegisterEmployee(toString(Number), toString(Lastname),
                    toString(Firstname),gend,Birth,toString(Email),username,
                    toString(Password),toString(PasswordConfirm),SelectedService,
                    Start,End,Picture,SelectedType,days,((CheckBox)findViewById(R.id.admin)).isChecked());




        }
        else if (v.getId() == R.id.register_reset_button)

            resetInput();
        else if (v.getId() == R.id.register_birthdate) {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

 //while(toString())
            picker = new DatePickerDialog(RegisterEmployee.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {

                        Birthdate.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year1));
                        Birth = "" + year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    },
                    year, month, day);

            picker.show();


        }

    }

        public  byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public  void resetInput() {

        Number.getText().clear();
        Lastname.getText().clear();
        Firstname.getText().clear();
        Birthdate.getText().clear();
        Email.getText().clear();
        Username.getText().clear();
        Password.getText().clear();
        PasswordConfirm.getText().clear();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        gend = (checkedId == R.id.register_boy) ? "M" : "F";

    }



    @Override
    public void onItemSelected(@NonNull AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.register_service) {
            //SelectedService = String.valueOf(spinnerServices.getSelectedItem());
            SelectedService = parent.getItemAtPosition(position).toString();


        }
        else if(parent.getId()==R.id.register_type) {
            //SelectedType=String.valueOf(spinnerTypes.getSelectedItem());
            SelectedType = parent.getItemAtPosition(position).toString();

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    @Override
    public void onRegisterEmployeeSuccess() {
        new ToastMessage(this,getString(R.string.registration_successful));
        resetInput();
    }

    @Override
    public void onRegisterEmployeeError(int errorNumber) {

        switch (errorNumber){
            case 0:
                new  ToastMessage(this,getString(R.string.number_required));
                break;
            case 1:

                new  ToastMessage(this,getString(R.string.number_invalid));
                break;
            case 2:
                new  ToastMessage(this,getString(R.string.last_name_required));
                break;
            case 3:
                new  ToastMessage(this,getString(R.string.last_name_invalid));
                break;
            case 4:

                new  ToastMessage(this,getString(R.string.firstname_required));
                break;
            case 5:

                new  ToastMessage(this,getString(R.string.firstname_invalid));
                break;
            case 6:

                new  ToastMessage(this,getString(R.string.mail_required));
                break;
            case 7:

                new  ToastMessage(this,getString(R.string.mail_invalid));
                break;
            case 8:
                new  ToastMessage(this,getString(R.string.username_required));
                break;
            case 9:
                new  ToastMessage(this,getString(R.string.username_invalid));
                break;
            case 10:
                new  ToastMessage(this,getString(R.string.password_required));
                break;
            case 11:
                new  ToastMessage(this,getString(R.string.password_invalid));
                break;
            case 12:
                new  ToastMessage(this,getString(R.string.chech_and_retry));
                break;
            case 13:
                new  ToastMessage(this,getString(R.string.no_workday_choosen));
                break;
            case 14:
                new  ToastMessage(this,getString(R.string.number_already_assigned));
                break;
            case 15:
                new  ToastMessage(this,getString(R.string.mail_already_assigned));
                break;
            case 16:
                new  ToastMessage(this,getString(R.string.username_already_assigned));
                break;

            default:
                break;
        }

    }

    @Override
    public void onShowHidePassword(int passwordId, int eyeId) {
         ImageView eye=findViewById(eyeId);
         EditText Password =findViewById(passwordId);
        if(Password.getTransformationMethod().
                equals(PasswordTransformationMethod.getInstance()))
        {
            eye.setImageResource(R.drawable.ic_baseline_visibility_off_18);

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
    public void sendEmail(String[] to, String qrCodeFileName,
                          String lastname, String firstname,
                          String username, String password, String gender) {


        String subject=getString(R.string.subject_registration);

        String message = gender + " " + firstname + " " + lastname +
                getString(R.string.has_been) + username + "\n" +
                getString(R.string.pass) + password + "\n" +
                getString(R.string.qr);


        File qrCodepicture =new File(getFilesDir(),qrCodeFileName);

        Uri qrCodeUri;


       qrCodeUri= FileProvider.getUriForFile(RegisterEmployee.this,
                getApplicationContext().getPackageName()+".provider", qrCodepicture);


        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("image/png");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.putExtra(Intent.EXTRA_STREAM,qrCodeUri);
        if(notice)
            startActivity(Intent.createChooser(emailIntent, "Envoyer avec"));


    }

    public String toString(@NonNull EditText e){
        return  e.getText().toString();

    }
    public void initView(){

        ImageView eyePwd = findViewById(R.id.register_show_password);
        ImageView eyePwdConfirm = findViewById(R.id.register_show_password_confirm);
        eyePwdConfirm.setOnClickListener(this);
        eyePwd.setOnClickListener(this);


        //new controller instance created
        registerEmployeePresenter = new RegisterEmployeeController(this);
        // The view gets service list from the controller
        String[] services = registerEmployeePresenter.onLoad();
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        SelectedType= employeTypes[0];
        SelectedService= services[0];
        Spinner spinnerServices = findViewById(R.id.register_service);
        Spinner spinnerTypes = findViewById(R.id.register_type);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);

         dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeTypes);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(dataAdapterR);


        NumberPicker start = findViewById(R.id.register_planning_start_choose);
        NumberPicker end = findViewById(R.id.register_planning_end_choose);
        start.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        end.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        Programm=findViewById(R.id.prog);
        Programm.setText(programm());
        Number = findViewById(R.id.register_number);
        Lastname = findViewById(R.id.register_lastname);
        Firstname = findViewById(R.id.register_firstname);
        RadioGroup gender = findViewById(R.id.register_gender);
        Birthdate = findViewById(R.id.register_birthdate);
        Birthdate.setClickable(true);
        Birthdate.setFocusable(false);
        Birthdate.setInputType(InputType.TYPE_NULL);
        Email = findViewById(R.id.register_email);
        Username = findViewById(R.id.register_username);
        Password = findViewById(R.id.register_password);
        PasswordConfirm = findViewById(R.id.register_password_confirm);
        circlePicture=findViewById(R.id.register_preview_image);
        Button register = findViewById(R.id.register_button);
        Button reset = findViewById(R.id.register_reset_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        gender.setOnCheckedChangeListener(this);
        Birthdate.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);
        selectPicture.setOnClickListener(this);

        spinnerServices.setOnItemSelectedListener(this);
        spinnerTypes.setOnItemSelectedListener(this);

        start.setFormatter(this);
        end.setFormatter(this);
        initNumberPicker(start,6,9);
        initNumberPicker(end,16,19);

                monday=findViewById(R.id.monday);
                tuesday=findViewById(R.id.tuesday);
                wednesday=findViewById(R.id.wednesday);
                thursday=findViewById(R.id.thursday);
                friday=findViewById(R.id.friday);
                satursday=findViewById(R.id.satursday);
                sunday=findViewById(R.id.sunday);
        myTable[0]=monday;
        myTable[1]=tuesday;
        myTable[2]=wednesday;
        myTable[3]=thursday;
        myTable[4]=friday;
        myTable[5]=satursday;
        myTable[6]=sunday;

        myTable[5].setChecked(false);
        myTable[6].setChecked(false);
    }

    public void initNumberPicker(NumberPicker n,int min,int max ){
                 if(n!=null) {
            n.setMinValue(min);
            n.setMaxValue(max);
            n.setWrapSelectorWheel(true);
            n.setOnValueChangedListener(this);

        }





    }

    @Override
    public void onValueChange(@NonNull NumberPicker picker, int oldVal, int newVal) {

        if(picker.getId()==R.id.register_planning_start_choose
                && 5<newVal && 10>newVal) {
            Start = newVal;
            Programm.setText(programm());
        }

        else if (picker.getId()==R.id.register_planning_end_choose && 15<newVal && 20>newVal) {
            End = newVal;
            Programm.setText(programm());
        }
                    }

    @Override
    public String format(int value) {
        String str;
        str=String.valueOf(value);
        if(value<10)
            str="0"+value;
        return str;
    }
    public String programm(){
        String str="";
        if(Start<10)
            str="0";
        str+=Start+":00-" ;
        if (End<10)
         str+="0";
        str+=End+":00";
        return str;

    }
    public byte[] workdays(){
        byte[] tab =new byte[7];
        int i;
       for(i=0;i<7;i++)
        {

            tab[i]= (byte) (myTable[i].isChecked()?'T':'F');


        }


return tab;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(@NonNull Editable s) {
        Username.setText(s.toString());

    }
}
