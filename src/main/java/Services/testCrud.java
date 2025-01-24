package Services;

import Entite.Payment;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class testCrud {
    public static void main(String[] args) {
        ServicePayment servicePayment = new ServicePayment();

        try {
            // 1. Add a new Payment
            Payment newPayment = new Payment(1, 101, 150.50, new Date(), "Completed", 5);
            servicePayment.ajouter(newPayment);

            // 2. Get all Payments
            System.out.println("Liste des paiements:");
            for (Payment p : servicePayment.getAll()) {
                System.out.println(p);
            }

            // 3. Get Payment by ID
            Payment retrievedPayment = servicePayment.getById(1);
            if (retrievedPayment != null) {
                System.out.println("Paiement récupéré: " + retrievedPayment);
            } else {
                System.out.println("Aucun paiement trouvé avec l'ID 1.");
            }

            // 4. Update a Payment
            if (retrievedPayment != null) {
                retrievedPayment.setAmount(200.75);
                retrievedPayment.setPaymentStatus("Pending");
                servicePayment.update(retrievedPayment);

                System.out.println("Paiement mis à jour:");
                System.out.println(servicePayment.getById(1));
            }

            // 5. Delete a Payment
            if (retrievedPayment != null) {
                servicePayment.supprimer(retrievedPayment);
                System.out.println("Paiement supprimé avec succès !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
