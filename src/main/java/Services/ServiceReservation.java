package Services;

import Entite.Reservation;
import Utils.DataSource;
import Entite.Activity;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ServiceReservation implements IService<Reservation> {

    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat;

    public ServiceReservation() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("INSERT INTO Reservation (memberId, activityId, reservationdate) VALUES (?, ?, ?);");
        pre.setInt(1, reservation.getMemberId());
        pre.setInt(2, reservation.getActivityId());
        pre.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));

        pre.executeUpdate();
        System.out.println("Réservation ajoutée.");
    }

    @Override
    public void supprimer(Reservation reservation) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM Reservation WHERE reservationId = ?");
        pre.setInt(1, reservation.getReservationId());
        pre.executeUpdate();
        System.out.println("Réservation supprimée.");
    }


    @Override
    public void update(Reservation reservation) throws SQLException {
        String query = "UPDATE Reservation SET memberId = ?, activityId = ?, reservationDate = ? WHERE reservationId = ?";
        PreparedStatement pre = conn.prepareStatement(query);

        pre.setInt(1, reservation.getMemberId());
        pre.setInt(2, reservation.getActivityId());
        pre.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
        pre.setInt(4, reservation.getReservationId());

        pre.executeUpdate();
        System.out.println("Réservation mise à jour.");
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        ResultSet reset = stat.executeQuery("SELECT * FROM Reservation");

        while (reset.next()) {
            int reservationId = reset.getInt(1);
            int memberId = reset.getInt(2);
            int activityId = reset.getInt(3);
            Date reservationDate = reset.getDate(4);

            Reservation r = new Reservation(reservationId, memberId, activityId, reservationDate);
            list.add(r);
        }
        return list;
    }

    @Override
    public Reservation getById(int id) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM Reservation WHERE reservationID = ?");
        pre.setInt(1, id);
        ResultSet reset = pre.executeQuery();

        Reservation r = null;

        if (reset.next()) {
            int reservationId = reset.getInt(1);
            int memberId = reset.getInt(2);
            int activityId = reset.getInt(3);
            Date reservationDate = reset.getDate(4);

            r = new Reservation(reservationId, memberId, activityId, reservationDate);
        } else {
            System.out.println("Aucune réservation trouvée avec l'ID : " + id);
        }

        reset.close();
        pre.close();

        return r;
    }

    public List<Reservation> getReservationsParMembre(int membreId) throws SQLException {
        List<Reservation> list = new ArrayList<>();
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM Reservation WHERE memberId = ?");
        pre.setInt(1, membreId);
        ResultSet reset = pre.executeQuery();

        while (reset.next()) {
            int reservationId = reset.getInt(1);
            int memberId = reset.getInt(2);
            int activityId = reset.getInt(3);
            Date reservationDate = reset.getDate(4);

            Reservation r = new Reservation(reservationId, memberId, activityId, reservationDate);
            list.add(r);
        }
        return list;
    }

    public List<Integer> getActivitesReserveesIds(int membreId) throws SQLException {
        List<Integer> list = new ArrayList<>();
        PreparedStatement pre = conn.prepareStatement("SELECT activityId FROM Reservation WHERE memberId = ?");
        pre.setInt(1, membreId);
        ResultSet reset = pre.executeQuery();

        while (reset.next()) {
            list.add(reset.getInt(1));
        }
        return list;
    }

    public void ajouterReservation(int membreId, int activityId) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("INSERT INTO Reservation (memberId, activityId, reservationdate) VALUES (?, ?, ?);");
        pre.setInt(1, membreId);
        pre.setInt(2, activityId);
        pre.setDate(3, new java.sql.Date(new Date().getTime()));

        pre.executeUpdate();
        System.out.println("Réservation ajoutée.");
    }
    public boolean hasTimeConflict(int memberId, Activity newActivity) throws SQLException {
        String query = "SELECT a.date, a.hour, a.duration " +
                "FROM Reservation r " +
                "JOIN Activity a ON r.activityId = a.activityId " +
                "WHERE r.memberId = ?";
        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, memberId);
        ResultSet rs = pre.executeQuery();

        // Convert the new activity's date and start time to LocalDate and LocalTime.
        // Assuming newActivity.getDate() returns a java.sql.Date
        LocalDate newActivityDate = ((java.sql.Date)newActivity.getDate()).toLocalDate();
        LocalTime newActivityStart = newActivity.getHour().toLocalTime();
        LocalTime newActivityEnd = newActivityStart.plusMinutes(newActivity.getDuration());

        while (rs.next()) {
            java.sql.Date sqlDate = rs.getDate("date");
            LocalDate existingDate = sqlDate.toLocalDate();
            if (existingDate.equals(newActivityDate)) {
                // If the dates are the same, check the time intervals.
                Time sqlTime = rs.getTime("hour");
                LocalTime existingStart = sqlTime.toLocalTime();
                int existingDuration = rs.getInt("duration");
                LocalTime existingEnd = existingStart.plusMinutes(existingDuration);

                // Check for overlapping intervals:
                // If newActivityStart is before the existing activity's end
                // and the existing activity's start is before newActivityEnd, there is a conflict.
                if (newActivityStart.isBefore(existingEnd) && existingStart.isBefore(newActivityEnd)) {
                    return true; // Conflict exists.
                }
            }
        }
        return false; // No conflicts found.
    }
}
