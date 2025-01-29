package Entite;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapCoachActivities {
    private Map<Member, List<Activity>> coachActivities;

    public MapCoachActivities() {
        coachActivities =new HashMap<>();
    }


    public boolean isConflit(Activity newActivity) {
        // Récupérer l'ID du coach associé à l'activité
        int coachId = newActivity.getMemberId();

        // Trouver le coach dans la carte
        Member coach = coachActivities.keySet()
                .stream()
                .filter(m -> m.getMemberId() == coachId)
                .findFirst()
                .orElse(null);

        // Récupérer les activités du coach
        List<Activity> activities = coachActivities.get(coach);

        if (activities == null || activities.isEmpty()) {
            return false; // Pas d'activités existantes pour ce coach
        }


        return activities.stream()
                .filter(existingActivity -> existingActivity.getDate().equals(newActivity.getDate()))
                .anyMatch(existingActivity -> {
                    Time startTime1 = existingActivity.getHour();
                    Time endTime1 = calculateEndTime(startTime1, existingActivity.getDuration());

                    Time startTime2 = newActivity.getHour();
                    Time endTime2 = calculateEndTime(startTime2, newActivity.getDuration());

                    return startTime1.before(endTime2) && endTime1.after(startTime2);
                });



    }

    // Méthode utilitaire pour calculer l'heure de fin d'une activité
    private Time calculateEndTime(Time startTime, int durationMinutes) {
        long endTimeInMillis = startTime.getTime() + durationMinutes * 60 * 1000; // Convertir les minutes en millisecondes
        return new Time(endTimeInMillis);
    }
}
