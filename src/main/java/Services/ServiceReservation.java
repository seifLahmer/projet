package Services;

import Entite.Payment;
import Entite.Reservation;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceReservation implements IService<Reservation>{

    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat= null ;

    public ServiceReservation() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        PreparedStatement pre= conn.prepareStatement("INSERT INTO Reservation (memberId,activityId,date) VALUES (?,?,? );");
        pre.setInt(1,reservation.getMemberId());
        pre.setInt(2,reservation.getActivityId());
        pre.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));

        pre.executeUpdate();
        System.out.println("reservation ajoutée");
    }

    @Override
    public void supprimer(Reservation reservation) throws SQLException {
        PreparedStatement pre= conn.prepareStatement("DELETE FROM Reservation WHERE reservationId = ?");
        pre.setInt(1,reservation.getReservationId());
        pre.executeUpdate();
        System.out.println("reservation supprimée");
    }

    @Override
    public void update(Reservation reservation, Map<String, Object> data) throws SQLException {
        // Construction de la requête SQL
        String query = "UPDATE Reservation SET ";
        query += String.join(" = ?, ", data.keySet()) + " = ? WHERE reservationId = ?";

        // Préparation de la requête
        PreparedStatement pre = conn.prepareStatement(query);

        // Ajout des valeurs dans le PreparedStatement
        int index = 1;
        for (Object value : data.values()) {
            pre.setObject(index++, value); // setObject simplifie la gestion des types
        }

        // Ajout de l'ID de réservation
        pre.setInt(index, reservation.getReservationId());

        // Exécution de la mise à jour
        pre.executeUpdate();
        System.out.println("Réservation mise à jour avec succès !");
    }




    @Override
    public List<Reservation> getAll() throws SQLException {
        List<Reservation> list= new ArrayList<>();

        ResultSet reset=stat.executeQuery("select * from Reservation");
        while (reset.next()) {
            int reservationId=reset.getInt(1);
            System.out.println(reservationId);
            int memberId=reset.getInt(2);
            int activityId=reset.getInt(3);
            Date reservationDate=reset.getDate(4);

            Reservation r=new Reservation(reservationId,memberId,activityId,reservationDate);

            list.add(r);
        }
        return list;
    }

    @Override
    public Reservation getById(int id) throws SQLException {

        PreparedStatement pre = conn.prepareStatement("SELECT * FROM Reservation WHERE reservationID = ?");
        pre.setInt(1, id);
        ResultSet reset = pre.executeQuery();

        Reservation r = null; // Initialiser la variable Reservation

        if (reset.next()) { // Vérifier s'il y a des résultats
            int reservationId = reset.getInt(1);
            System.out.println(reservationId);
            int memberId = reset.getInt(2);
            int activityId = reset.getInt(3);
            Date reservationDate = reset.getDate(4);

            r = new Reservation(reservationId, memberId, activityId, reservationDate);
        } else {
            System.out.println("Aucune réservation trouvée avec l'ID : " + id);
        }

        reset.close(); // Fermer le ResultSet
        pre.close(); // Fermer le PreparedStatement

        return r; // Retourner l'objet Reservation ou null si aucune réservation n'a été trouvée
    }
}
