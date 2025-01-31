package Services;

import Entite.Abonnement;
import Entite.Payment;
import Services.ServiceAbonnement;
import Services.ServicePayment;
import java.sql.SQLException;
import java.util.Date;

public class testCrud {
    public static void main(String[] args) {
        try {
            // Initialisation des services
            ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
            ServicePayment servicePayment = new ServicePayment();

            // 🔹 1. Tester l'ajout d'un abonnement
            System.out.println("🔹 Ajout d'un abonnement...");
            Abonnement newAbonnement = new Abonnement(
                    1, // AbonnementId
                    101, // MemberId
                    new Date(), // StartDate
                    new Date(2025 - 1900, 0, 30), // EndDate (ATTENTION : l'année commence à 1900 en Java)
                    100.0, // Price
                    "En attente", // Status
                    5 // AdminId
            );
            // Appel de la méthode souscrireAbonnement avec les bons arguments
            Abonnement abonnementSouscrit = serviceAbonnement.souscrireAbonnement(
                    1,  // abonnementId
                    101, // memberId
                    5,   // adminId
                    100.0, // price
                    12   // dureeMois (exemple : 12 mois)
            );

// Affichage de la confirmation
            System.out.println("✅ Abonnement ajouté avec succès : " + abonnementSouscrit);


            // 🔹 2. Tester l'ajout d'un paiement
            System.out.println("🔹 Ajout d'un paiement...");
            Payment newPayment = new Payment(
                    1, // PaymentId
                    101, // MemberId
                    100.0, // Amount
                    new Date(), // PaymentDate
                    "Effectué", // PaymentStatus
                    1, // AbonnementId
                    5 // AdminId
            );
            servicePayment.ajouter(newPayment);
            System.out.println("✅ Paiement ajouté avec succès.\n");

            // 🔹 3. Tester l'affichage des abonnements
            System.out.println("🔹 Liste des abonnements :");
            serviceAbonnement.getAll().forEach(System.out::println);

            // 🔹 4. Tester l'affichage des paiements
            System.out.println("\n🔹 Liste des paiements :");
            servicePayment.getAll().forEach(System.out::println);

            // 🔹 5. Tester la mise à jour d'un abonnement
            System.out.println("\n🔹 Renouvellement de l'abonnement...");
            serviceAbonnement.renouvelerAbonnement(1, 3);
            System.out.println("Abonnement renouvelé.\n");

            // 🔹 6. Tester la mise à jour d'un paiement
            System.out.println("\n🔹 Mise à jour du paiement...");
            newPayment.setAmount(120.0);
            servicePayment.update(newPayment);
            System.out.println("✅ Paiement mis à jour avec succès.\n");

            // 🔹 7. Tester la suppression d'un paiement
            System.out.println("\n🔹 Suppression du paiement...");
            servicePayment.supprimer(1);
            System.out.println(" Paiement supprimé avec succès.\n");

            // 🔹 8. Tester la suppression d'un abonnement
            System.out.println("\n🔹 Annulation de l'abonnement...");
            serviceAbonnement.annulerAbonnement(1);
            System.out.println(" Abonnement annulé avec succès.");

        } catch (SQLException e) {
            System.out.println(" Erreur SQL : " + e.getMessage());
        }
    }
}
