package entity;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Represents an employee.
 *<br/>
 * Proposes employee attributes and exposes  methods to manage an employee.<br/>
 *
 * Employee has methods to get and set employees informations and methods and
 * attributes to check if an employee
 * is valid or not
        * @author Ezéchiel G. ADEDE
        * @version 1.0
        * @since 2023

        */
public class Employee implements IEmployee {
    /** The employee has an empty registration number
     */
    public final static int EMPTY_NUMBER=13;
    /**The employee has an invalid registration number**/
    public final static int INVALID_NUMBER=1;
    /**The employee has an empty lastname**/
    public final static int EMPTY_LASTNAME=2;
    /**The employee has an invalid lastname**/
    public final static int INVALID_LASTNAME=3;
    /**The employee has an empty firstname**/
    public final static int EMPTY_FIRSTNAME=4;
    /**The employee has an invalid firstname**/
    public final static int INVALID_FIRSTNAME=5;
    /**The employee has an empty username**/
    public final static int EMPTY_USERNAME=6;
    /**The employee has an invalid username**/
    public final static int INVALID_USERNAME=11;
    /**The employee has an empty password**/
    public final static int EMPTY_PASSWORD=9;
    /**The employee has an invalid**/
    public final static int INVALID_PASSWORD=10;
    /**The employee has an empty mail address**/
    public final static int EMPTY_MAIL=14;
    /**The employee has an invalid mail address**/
    public final static int INVALID_MAIL=12;
    /**The employee has an empty birthdate**/
    public final static int EMPTY_BIRTHDATE=15;

    //Personal informations on employee
    /**Represents the registration number of the employee.
     *It must be a a non-null naturel number**/
    private int RegistrationNumber;
    /**Represents the firstname of the employee and It must not be empty**/
    private String Firstname;
    /**Represents the current attendance status of the employeee.
     *It must be one of:
     * <ul>
     *<li> "Absent" when the employee has not already clocked in the current day</li>
     *<li> "Late" when the employee has clocked in the urrent day but in late</li>
     *<li> "Present" when he has clocked in the current day and on time</li>
     *<li>  "Out" when he doesn't have to work the current day</li>
     *  </ul>
     **/
    private String CurrentStatus;
    /**Represents the lastname of the employee and must not be empty**/
    private String Lastname;
    /**Represents the gender of the employee:
     * either 'M' for male or 'F' for famale**/
    private char Gender;
    /**Represents the birthdate of the imployee with the format YYYY-MM-dd**/
    private String Birthdate;
    /**Represents the mail address of the employee **/
    private String MailAddress;
    /**Represents the picture the employee **/
    private byte[] Picture;
    /**Represents the username of the employee and must not be empty**/
    private String Username;
    /**Represents the password of the employee and must not be empty**/
    private String Password;
    /**Represents the function of the employee and must not be empty**/
    private String Function;
    /**Represents the workdays of the employee and must not be empty**/
    private byte[] Workdays;
    /**Represents the day when the employee has been created**/
    private  String AddDate;
    /**Wether the employee is an admin or not**/
    private  boolean isAdmin;
    /**Make an employee admin or not
     *@param admin : if it's true the employee becomes admin
     *             else he becomes simple employee
     * **/
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    //Constructeurs

/**A constructor of employee
 *@param  registrationNumber is the registration number of the employee
 * @param  lastname  is his lastname
 * @param  firstname is  his firstname(s)
 * @param gender is his gender: 'M' or 'F'
 * @param  birthdate is his birthdate with the format YYYY-MM-dd
 * @param mailAddress is his mail address
 * @param picture represents the eventual picture
 *  @param username is the username of the employee
 * @param password: his password
 * @param function is his function
 * **/
    public Employee(int registrationNumber, String lastname,
                    String firstname, char gender, String birthdate,
                 String mailAddress, byte[] picture,  String username,
                    String password,String function) {

        RegistrationNumber = registrationNumber;

        Firstname = firstname;
        Lastname = lastname;
        Gender = gender;
        Birthdate = birthdate;
        MailAddress = mailAddress;
        Picture = picture;
        Username = username;
        Password = password;
        Function = function;

    }
    /**@return  the day when the employee has been created**/
    public String getAddDate() {
        return AddDate;
    }

    /**  Sets addDate to employee
     * @param addDate: the day when the employee has been created**/
    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    /**A constructor of employee: construct an employee without setting a picture
     *@param  registrationNumber is the registration number of the employee
     * @param  lastname  is his lastname
     * @param  firstname is  his firstname(s)
     * @param gender is his gender: 'M' or 'F'
     * @param  birthdate is his birthdate with the format YYYY-MM-dd
     * @param mailAddress is his mail address
     *  @param username is the username of the employee
     * @param password: his password
     * @param function is his function
     * **/
    public Employee(int registrationNumber, String lastname, String firstname,

                    char gender, String birthdate, String mailAddress, String username,
                    String password, String function) {

        this(registrationNumber, lastname,firstname, gender,
                birthdate, mailAddress, null,  username, password, function);


    }
    /**A constructor of employee for login
     *  @param username is the username of the employee
     * @param password: his password
     * **/
    public Employee(String username,String password){
        Username=username;
        Password=password;
    }
    /**A constructor of employee
     *@param  registrationNumber is the registration number of the employee
     * **/
    public Employee(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    //getters
    /**@return the registration number of the employee**/
    public int getRegistrationNumber() {
        return RegistrationNumber;
    }
    /**@return  the firstname of the employee*/
    public String getFirstname() {
        return Firstname;
    }
    /**@return  the lastname of the employee**/
    public String getLastname() {
        return Lastname;
    }
    /**@return  the gender of the employee**/
    public char getGender() {
        return Gender;
    }

    /**@return  the birthdate of the employee**/
    public String getBirthdate() {
        return Birthdate;
    }
    /**@return  the mail adress of the employee**/
    public String getMailAddress() {
        return MailAddress;
    }
    /**@return  the picture of the employee**/
    public  byte[] getPicture() {
        return Picture;
    }
    /**@return  the username of the employee**/
    public  String getUsername(){
        return  Username;
    }
    /**@return  the hashed password of the employee**/
    public  String getPassword(){
        return  Password;
    }
    /**@return  the function of the employee**/
    public String getFunction(){ return Function;}
    /**@return  the workdays of the employee**/
    public byte[] getWorkdays() {
        return Workdays;
    }
   /**  Sets current attendance status to employee
 * @param currentStatus: the current attendance status of the employee**/
    public void setCurrentStatus(String currentStatus) {
        CurrentStatus = currentStatus;
    }
    /**  Sets workdays to employee
     * @param workdays: the workdays of the employee**/
    public void setWorkdays(byte[] workdays) {
        Workdays = workdays;
    }
    /**@return  the current status of the employee**/
    public String getCurrentStatus() {
        return CurrentStatus;
    }
/***Check if an employee has valid properties
 * @return an error code if there is a wrong property and 0 otherwise */
    @Override
    public int isValid() {
        if(TextUtils.isEmpty(""+RegistrationNumber))
            return EMPTY_NUMBER;
        else if(hasInvalidNumber())
            return INVALID_NUMBER;
        else if(TextUtils.isEmpty(Lastname))
            return EMPTY_LASTNAME;
        else if (hasInvalidLastName())
            return INVALID_LASTNAME;
       else if(TextUtils.isEmpty(Firstname))
            return EMPTY_FIRSTNAME;
        else if (hasInvalidFirstname())
            return INVALID_FIRSTNAME;
        /*else if(TextUtils.isEmpty(Birthdate))
            return EMPTY_BIRTHDATE;*/
        else if (TextUtils.isEmpty(MailAddress))
            return EMPTY_MAIL;
        else if(hasInvalidEmail())
            return INVALID_MAIL;
        else if(TextUtils.isEmpty(Username))
            return EMPTY_USERNAME;
        else if (hasInvalidUsername())
            return INVALID_USERNAME;
        else if (TextUtils.isEmpty(Password))
            return EMPTY_PASSWORD;
        else if(hasInvalidPassword())
            return  INVALID_PASSWORD;

        return 0;
    }
/**Sets password to employee
 * @param password: the password of the employee**/
    @Override
    public void setPassword(String password) {
        Password=password;

    }


    //Setters

    /**Sets registration numlber to employee
     * @param registrationNumber: the registration number of the employee**/
    public void setRegistrationNumber(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    /**Sets firstname to employee
     * @param firstname: the firstname of the employee**/
    public void setFirstname(String firstname) {
        Firstname = firstname;

    }
    /**Sets lastname to employee
     * @param lastname: the lastname of the employee**/
    public void setLastname(String lastname) {
        Lastname = lastname;
    }
    /**Sets gender to employee
     * @param gender: the gender of the employee**/
    public void setGender(char gender) {
        Gender = gender;
    }
    /**Sets biirthdate to employee
     * @param birthdate: the birthdate of the employee**/
    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }
    /**Sets mail address to employee
     * @param mailAddress: the mail address of the employee**/
    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;

    }
    /**Sets picture to employee
     * @param picture: the picture of the employee**/
    public void setPicture(byte[] picture) {
        Picture = picture;
    }
    /**Sets function to employee
     * @param function: the function of the employee**/
    public void setFunction(String function){
        Function = function;
    }
    /**Sets username to employee
     * @param username: the username of the employee**/
    public void setUsername(String username){
        Username=username;

    }
    /**Checks if an employee has a valid username or not
     * @return true if the username is invalid and false otherwise**/
    public  boolean hasInvalidUsername() {


        return !Username.matches("^[A-Z][A-Za-z0-9]{5,29}$")&& !hasInvalidEmail()
                &&!Patterns.EMAIL_ADDRESS.matcher(Username).matches();
    }
    /**Checks if an employee has a valid lastname or not
     * @return true if the lastname is invalid and false otherwise**/
    public boolean hasInvalidLastName() {
        String pattern = "^[A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][A-Za-zâêîûôáéíúóàèùìòç]" +

                "+([-' ][A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+)?";
        return !Lastname.matches(pattern);



    }
    /**Checks if an employee has a valid firstname or not
     * @return true if the firstname is invalid and false otherwise**/
    public boolean hasInvalidFirstname(){
        String pattern ="^[A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+" +
                "([-'][A-ZÂÊÛÎÔÁÉÚÍ ÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+)?";
        return !Firstname.matches(pattern);


    }
    /**Checks if an employee has a valid registration number or not
     * @return true if the registration number is invalid and false otherwise**/
    public boolean hasInvalidNumber(){
        return !String.valueOf(RegistrationNumber).matches("^[0-9]+$");


    }
    /**Checks if an employee has a valid mail address or not
     * @return true if the mail address is invalid and false otherwise**/
    public boolean hasInvalidEmail() {
return  !Patterns.EMAIL_ADDRESS.matcher(MailAddress).matches();
    }
    /**Checks if an employee has a valid password or not
     * @return true if the password is invalid and false otherwise**/
    public  boolean hasInvalidPassword() {

        return !(
                Password.length()>=6&&
                isInTheRange("[A-Z]")&&
                isInTheRange("[a-z]")&&
                isInTheRange("[0-9]")&&
                isInTheRange("\\W")
        );
    }
    /****/
    private int nb_occur(String str, String pattern){

        Matcher matcher= Pattern.compile(pattern).matcher(str);
        int count=0;
        while(matcher.find())
            count++;
        return count;
    }
    /****/
    private boolean isInTheRange(String pattern){
        return 1<= nb_occur(Password,pattern);


    }
    /**Checks if an employee is an admin or not
     * @return true if the employee is an admin  and false otherwise**/
    public boolean isAdmin() {
        return isAdmin;
    }
/**Check if an employee has valid  user properties (username and password)<br/>
 *
 * It's used when we wanna create a new employee for example
 * @return an error code if ther is awrong property and 0 otherwise */
    public int validUser() {
        if(Objects.equals(getUsername(), ""))
            return EMPTY_USERNAME;
        if(hasInvalidUsername())
            return  INVALID_USERNAME;
        if(Objects.equals(getPassword(), ""))
            return EMPTY_PASSWORD;
        if(hasInvalidPassword())
            return INVALID_PASSWORD;

        return 0;
    }
}