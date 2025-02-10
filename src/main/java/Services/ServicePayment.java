package Services;

import Entite.Payment;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicePayment {
    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat = null;

    public ServicePayment() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouter(Payment payment) throws SQLException {
        String query = "INSERT INTO Payment (MemberId, Amount, PaymentDate, PaymentStatus, abonnementID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = conn.prepareStatement(query);

        pre.setInt(1, payment.getMemberId());
        pre.setDouble(2, payment.getAmount());
        pre.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
        pre.setString(4, payment.getPaymentStatus());
        pre.setInt(5, payment.getAbonnementId());

        pre.executeUpdate();
        System.out.println("Paiement ajouté avec succès !");
    }

    public void supprimer(Payment payment) throws SQLException {
        String query = "DELETE FROM Payment WHERE PaymentId = ?";
        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, payment.getPaymentId());
        pre.executeUpdate();
        System.out.println("Paiement supprimé avec succès !");
    }

    public void update(Payment payment) throws SQLException {
        String query = "UPDATE Payment SET MemberId = ?, Amount = ?, PaymentDate = ?, PaymentStatus = ? WHERE PaymentId = ?";
        PreparedStatement pre = conn.prepareStatement(query);

        pre.setInt(1, payment.getMemberId());
        pre.setDouble(2, payment.getAmount());
        pre.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
        pre.setString(4, payment.getPaymentStatus());
        pre.setInt(5, payment.getPaymentId());

        pre.executeUpdate();
        System.out.println("Paiement mis à jour avec succès !");
    }

    public Payment getById(int id) throws SQLException {
        String query = "SELECT * FROM Payment WHERE PaymentId = ?";
        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        
        if (rs.next()) {
            int memberId = rs.getInt("MemberId");
            double amount = rs.getDouble("Amount");
            Date paymentDate = rs.getDate("PaymentDate");
            String paymentStatus = rs.getString("PaymentStatus");
            int abonnementId = rs.getInt("AbonnementID");
            
            Payment payment = new Payment(memberId, amount, paymentDate, paymentStatus, abonnementId);
            payment.setPaymentId(id);
            return payment;
        }
        return null;
    }

    public List<Payment> getAll() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = stat.executeQuery("SELECT * FROM Payment");
        
        while (rs.next()) {
            int paymentId = rs.getInt("PaymentId");
            int memberId = rs.getInt("MemberId");
            double amount = rs.getDouble("Amount");
            Date paymentDate = rs.getDate("PaymentDate");
            String paymentStatus = rs.getString("PaymentStatus");
            int abonnementId = rs.getInt("AbonnementID");

            Payment payment = new Payment(memberId, amount, paymentDate, paymentStatus, abonnementId);
            payment.setPaymentId(paymentId);
            payments.add(payment);
        }
        return payments;
    }
}
