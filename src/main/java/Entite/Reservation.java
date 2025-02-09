package Entite;

import Services.ServiceActivity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Reservation {
    private int reservationId;
    private int memberId;
    private int activityId;
    private Date reservationDate;



    public Reservation(int reservationId, int memberId, int activityId, Date reservationDate) throws SQLException {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.activityId = activityId;
        this.reservationDate = reservationDate;
    }

    public Reservation(int memberId, int activityId, Date reservationDate) throws SQLException {

        this.memberId = memberId;
        this.activityId = activityId;
        this.reservationDate = reservationDate;
    }


    public Reservation(Date reservationDate) throws SQLException {
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

    public String getActivityName() {
        ServiceActivity sa = new ServiceActivity();
        try {
            ActivityList al = new ActivityList(sa.getAll()); // Charge la liste des activités
            return al.getNameById(activityId,memberId); // Récupère le nom via la liste
        } catch (SQLException e) {
            System.out.println(e);
            return "Erreur";
        }
    }



}
