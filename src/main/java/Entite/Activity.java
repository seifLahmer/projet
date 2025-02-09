package Entite;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Activity {
    private int activityId;
    private String activityName;
    private String description;
    private int maxMembers;
    private Date date ;
    private Time hour ;
    private int duration ;
    private int memberId ;

    public Activity(String name, String description, int capacity, Date date, Time hour, int duration , int memberId) {
        this.activityName = name;
        this.description = description;
        this.maxMembers = capacity;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.memberId = memberId;
    }

    public Activity(int activityId, String activityName, String description, int maxMembers, Date date, Time hour, int duration, int memberId) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.description = description;
        this.maxMembers = maxMembers;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.memberId = memberId;
    }

    public int getActivityId() {
        return activityId;
    }

    public Activity(int activityId, String name, String description, int capacity, Date date, Time hour, int duration) {
        this.activityId = activityId;
        this.activityName = name;
        this.description = description;
        this.maxMembers = capacity;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Activity(String name, String description, int capacity) {
        this.activityName = name;
        this.description = description;
        this.maxMembers = capacity;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}


















