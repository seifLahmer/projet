package Test;

import Entite.Activity;
import Entite.Reservation;
import Services.ServiceReservation;
import Services.ServiceActivity;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

public class Test {
    public static void main(String[] args) throws SQLException {
        try {
            // Création d'une instance du service
            ServiceReservation sr = new ServiceReservation();

            // Ajout de réservations
            System.out.println("Ajout des réservations...");
            Reservation r1 = new Reservation(1, 1, new Date(2025 - 1900, 7, 15));
            Reservation r2 = new Reservation(1, 2, new Date(2025 - 1900, 8, 15));
            Reservation r3 = new Reservation(2, 1, new Date(2025 - 1900, 9, 15));
            sr.ajouter(r1);
            sr.ajouter(r2);
            sr.ajouter(r3);

            // Affichage de toutes les réservations
            System.out.println("\nListe des réservations :");
            sr.getAll().forEach(System.out::println);

            // Mise à jour d'une réservation
            System.out.println("\nMise à jour de la réservation r1...");
            Map<String, Object> data = new HashMap<>();
            data.put("activityId", 3); // Nouvelle activité
            data.put("reservationDate", new Date(2025 - 1900, 11, 10));


            // Affichage de la réservation mise à jour
            System.out.println("\nRéservation mise à jour :");
            System.out.println(sr.getById(r1.getReservationId()));

            // Suppression d'une réservation
            System.out.println("\nSuppression de la réservation r3...");
            sr.supprimer(r3);

            // Affichage des réservations restantes
            System.out.println("\nListe des réservations après suppression :");
            sr.getAll().forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }



        try {
            // Création d'une instance de ServiceActivity
            ServiceActivity sa = new ServiceActivity();

            // Création d'activités
            Activity a1 = new Activity("Yoga", "Session de yoga en groupe", 15, new Date(2025 - 1900, 7, 10), Time.valueOf("10:00:00"), 90,1);
            Activity a2 = new Activity( "Natation", "Cours de natation pour débutants", 10, new Date(2025 - 1900, 7, 15), Time.valueOf("14:00:00"), 60,1);
            Activity a3 = new Activity( "Zumba", "Cours de Zumba dynamique", 20, new Date(2025 - 1900, 7, 20), Time.valueOf("18:00:00"), 120,1);

            // Ajouter les activités
            sa.ajouter(a1);
            sa.ajouter(a2);
            sa.ajouter(a3);

            // Supprimer une activité
            sa.supprimer(a3);

            // Mise à jour d'une activité (a1)
            Map<String, Object> data = new HashMap<>();
            data.put("activityName", "Yoga Avancé");
            data.put("description", "Session de yoga avancée avec méditation");
            data.put("maxMembers", 20);
            data.put("date", new Date(2025 - 1900, 8, 1)); // Nouvelle date
            data.put("hour", Time.valueOf("11:00:00")); // Nouvel horaire
            data.put("duration", 120); // Nouvelle durée


            // Affichage de toutes les activités
            List<Activity> allActivities = sa.getAll();
            System.out.println("Liste des activités :");
            for (Activity activity : allActivities) {
                System.out.println(activity);
            }

            // Récupération d'une activité par ID
            Activity activityById = sa.getById(2);
            if (activityById != null) {
                System.out.println("Activité avec ID 2 : " + activityById);
            } else {
                System.out.println("Aucune activité trouvée avec cet ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
