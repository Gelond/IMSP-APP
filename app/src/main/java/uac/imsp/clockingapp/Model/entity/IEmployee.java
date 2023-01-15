package entity;
/** Provides interface for Employee
 *@see  Employee**/
public interface IEmployee {
     int getRegistrationNumber() ;

     String getFirstname();

     String getLastname() ;

     char getGender() ;

     String getBirthdate() ;
     String getMailAddress() ;
      byte[] getPicture() ;

     String getUsername();
     String getPassword();

     int isValid();


    void setPassword(String password);
    void setUsername(String username);
    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setMailAddress(String mailAddress);
    void setGender(char gender);
    void setRegistrationNumber(int registrationNumber);
    void setPicture(byte[] picture);
}
