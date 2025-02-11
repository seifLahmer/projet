package Entite;

import java.util.Date;

public class Member {
    private int MemberId;
    private String FirstName ;
    private String LastName ;
    private String Email ;
    private String Password ;
    private char Gender ;
    private String PhoneNumber ;
    private String schedule ;
    private Date StartDate ;
    private Date EndDate ;
    private float Price ;
    private boolean Status ;
    private String SubscriptionType ;
    private String role ;

    public Member(int MemberId , String FirstName , String LastName, String Email, String Password,char Gender, String PhoneNumber, String schedule, Date StartDate, Date EndDate, float Price, boolean Status, String subscriptionType, String role) {
        this.MemberId = MemberId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
        this.Gender = Gender;
        this.PhoneNumber = PhoneNumber;
        this.schedule = schedule;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Price = Price;
        this.Status = Status;
        this.SubscriptionType = subscriptionType;
        this.role = role;

    }
    public Member( String FirstName , String LastName, String Email, String Password,char Gender, String PhoneNumber, String schedule, Date StartDate, Date EndDate, float Price, boolean Status, String subscriptionType, String role) {

        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
        this.Gender = Gender;
        this.PhoneNumber = PhoneNumber;
        this.schedule = schedule;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Price = Price;
        this.Status = Status;
        this.SubscriptionType = subscriptionType;
        this.role = role;

    }

    public int getMemberId() {
        return MemberId;
    }
    public void setMemberId(int MemberId) {
        this.MemberId = MemberId;
    }
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public String getLastName() {
        return LastName;

    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public char getGender() {
        return Gender;
    }
    public void setGender(char Gender) {
        this.Gender = Gender;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public java.sql.Date getStartDate() {
        return (java.sql.Date) StartDate;
    }
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }
    public java.sql.Date getEndDate() {
        return (java.sql.Date) EndDate;
    }
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }
    public float getPrice() {
        return Price;
    }
    public void setPrice(float Price) {
        this.Price = Price;
    }
    public boolean isStatus() {
        return Status;
    }
    public void setStatus(boolean Status) {
        this.Status = Status;
    }
    public boolean getStatus() {
        return Status;
    }
    public String getSubscriptionType() {
        return SubscriptionType;
    }
    public void setSubscriptionType(String subscriptionType) {
        SubscriptionType = subscriptionType;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getPassword() {
        return this.Password;
    }



}
