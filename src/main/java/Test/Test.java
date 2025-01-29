package Test;

import Entite.Activity;
import Entite.Reservation;
import Services.ServiceReservation;
import Services.ServiceActivity;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import Services.ServiceReservation;
import Entite.Reservation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
public class Test {
    public static void main(String[] args) throws SQLException {



                // Instanciation du service
                ServiceReservation serviceReservation = new ServiceReservation();

                try {
                    // 1. Création d'une nouvelle réservation
                    System.out.println("=== Ajouter une réservation ===");
                    Reservation newReservation = new Reservation(1, 2, new Date());
                    serviceReservation.ajouter(newReservation);

                    // 2. Récupérer toutes les réservations
                    System.out.println("\n=== Liste de toutes les réservations ===");
                    List<Reservation> reservations = serviceReservation.getAll();
                    for (Reservation res : reservations) {
                        System.out.println("Reservation ID: " + res.getReservationId() +
                                ", Member ID: " + res.getMemberId() +
                                ", Activity ID: " + res.getActivityId() +
                                ", Date: " + res.getReservationDate());
                    }

                    // 3. Récupérer une réservation par ID
                    System.out.println("\n=== Récupérer une réservation par ID ===");
                    int searchId = reservations.get(0).getReservationId(); // Exemple : récupérer l'ID du premier élément
                    Reservation resById = serviceReservation.getById(searchId);
                    if (resById != null) {
                        System.out.println("ID trouvée : " + resById.getReservationId() +
                                ", Date : " + resById.getReservationDate());
                    }

                    // 4. Mise à jour d'une réservation
                    System.out.println("\n=== Mettre à jour une réservation ===");
                    if (!reservations.isEmpty()) {
                        Reservation toUpdate = reservations.get(0); // Exemple : prendre la première réservation
                        toUpdate.setActivityId(3); // Changer l'ID de l'activité
                        serviceReservation.update(toUpdate);
                        System.out.println("Mise à jour : Activité changée à 3 pour Reservation ID = " + toUpdate.getReservationId());
                    }

                    // 5. Supprimer une réservation
                    System.out.println("\n=== Supprimer une réservation ===");
                    if (!reservations.isEmpty()) {
                        Reservation toDelete = reservations.get(0); // Exemple : prendre la première réservation
                        serviceReservation.supprimer(toDelete);
                        System.out.println("Réservation ID = " + toDelete.getReservationId() + " supprimée.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



