package Entite;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private int memberId;
    private int activityId;
    private Date reservationDate;

    public Reservation(int reservationId, int memberId, int activityId, Date reservationDate) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.activityId = activityId;
        this.reservationDate = reservationDate;
    }

    public Reservation(int memberId, int activityId, Date reservationDate) {

        this.memberId = memberId;
        this.activityId = activityId;
        this.reservationDate = reservationDate;
    }


    public Reservation(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
}
