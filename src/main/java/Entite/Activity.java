package Entite;

import java.sql.Time;
import java.util.Date;

public class Activity {
    private int activityId;
    private String name;
    private String description;
    private int capacity;
    private Date date ;
    private Time hour ;
    private int duration ;

    public Activity(String name, String description, int capacity, Date date, Time hour, int duration) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
    }

    public int getActivityId() {
        return activityId;
    }

    public Activity(int activityId, String name, String description, int capacity, Date date, Time hour, int duration) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
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
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
