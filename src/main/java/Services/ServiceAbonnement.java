package Services;

import Entite.Abonnement;
import Utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceAbonnement {
    private static Connection conn = DataSource.getInstance().getCon();
    private static Statement stat = null;

    public ServiceAbonnement() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Ajouter un abonnement
    public static void ajouter(Abonnement abonnement) throws SQLException {
        String query = "INSERT INTO abonnement (startDate, endDate, price, status, memberId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = conn.prepareStatement(query)) {
            pre.setDate(1, Date.valueOf(abonnement.getStartDate()));
            pre.setDate(2, Date.valueOf(abonnement.getEndDate()));
            pre.setDouble(3, abonnement.getPrice());
            pre.setString(4, abonnement.getStatus());
            pre.setInt(5, abonnement.getMemberId());
            pre.executeUpdate();
        }
    }

    // Supprimer un abonnement
    public static void supprimer(Abonnement abonnement) throws SQLException {
        String query = "DELETE FROM abonnement WHERE abonnementId=?";
        try (PreparedStatement pre = conn.prepareStatement(query)) {
            pre.setInt(1, abonnement.getAbonnementId());
            pre.executeUpdate();
        }
    }

    // Mettre à jour un abonnement
    public static void update(Abonnement abonnement) throws SQLException {
        String query = "UPDATE abonnement SET startDate=?, endDate=?, price=?, status=?, memberId=? WHERE abonnementId=?";
        try (PreparedStatement pre = conn.prepareStatement(query)) {
            pre.setDate(1, Date.valueOf(abonnement.getStartDate()));
            pre.setDate(2, Date.valueOf(abonnement.getEndDate()));
            pre.setDouble(3, abonnement.getPrice());
            pre.setString(4, abonnement.getStatus());
            pre.setInt(5, abonnement.getMemberId());
            pre.setInt(6, abonnement.getAbonnementId());
            pre.executeUpdate();
        }
    }

    // Récupérer tous les abonnements
    public static List<Abonnement> getAll() throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Abonnement abonnement = new Abonnement(
                    rs.getInt("abonnementId"),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    rs.getDouble("price"),
                    rs.getString("status"),
                    rs.getInt("memberId")
                );
                abonnements.add(abonnement);
            }
        }
        return abonnements;
    }

    // Récupérer un abonnement par son ID
    public Abonnement getById(int id) throws SQLException {
        String query = "SELECT * FROM abonnement WHERE abonnementId = ?";
        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, id);

        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            int abonnementId = rs.getInt("abonnementId");
            LocalDate startDate = rs.getDate("startDate").toLocalDate();
            LocalDate endDate = rs.getDate("endDate").toLocalDate();
            double price = rs.getDouble("price");
            String status = rs.getString("status");
            int memberId = rs.getInt("memberId");

            return new Abonnement(abonnementId, startDate, endDate, price, status, memberId);
        }

        return null;
    }
}
