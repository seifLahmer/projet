package Services;

import Entite.Activity;
import Entite.Reservation;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceActivity implements IService<Activity>{
    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat= null ;

    public ServiceActivity() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Activity activity) throws SQLException {
        PreparedStatement pre= conn.prepareStatement("INSERT INTO Activity (name,description,capacity, date,hour,duration) VALUES (?,?,?,?,?,? );");
        pre.setInt(3,activity.getCapacity());
        pre.setString(2,activity.getDescription());
        pre.setString(1, activity.getName());
        pre.setDate(4, new java.sql.Date(activity.getDate().getTime()));
        pre.setTime(5,activity.getHour());
        pre.setInt(6,activity.getDuration());

        pre.executeUpdate();
        System.out.println("activite ajoutée");
    }

    @Override
    public void supprimer(Activity activity) throws SQLException {
        PreparedStatement pre= conn.prepareStatement("DELETE FROM Activity WHERE activityId = ?");
        pre.setInt(1,activity.getActivityId());
        pre.executeUpdate();
        System.out.println("activite supprimée");
    }

    @Override
    public void update(Activity activity, Map<String, Object> data) throws SQLException {
        // Construction de la requête SQL
        String query = "UPDATE Activity SET ";
        query += String.join(" = ?, ", data.keySet()) + " = ? WHERE activityId = ?";

        // Préparation de la requête
        PreparedStatement pre = conn.prepareStatement(query);

        // Ajout des valeurs dans le PreparedStatement
        int index = 1;
        for (Object value : data.values()) {
            pre.setObject(index++, value); // setObject simplifie la gestion des types
        }

        // Ajout de l'ID de l'activité (clé primaire)
        pre.setInt(index, activity.getActivityId());

        // Exécution de la requête
        pre.executeUpdate();
        System.out.println("Activité mise à jour avec succès !");
    }


    @Override
    public List<Activity> getAll() throws SQLException {
        List<Activity> list= new ArrayList<>();

        ResultSet reset=stat.executeQuery("select * from Activity");
        while (reset.next()) {
            int activityId=reset.getInt(1);
            String name=reset.getString(2);
            String description  =reset.getString(3);
            int capacite = reset.getInt(4);
            Date date=reset.getDate(5);
            Time hour = reset.getTime(6);
            int duration = reset.getInt(7);

            Activity r=new Activity(activityId,name,description,capacite,date,hour,duration);

            list.add(r);
        }
        return list;
    }
    @Override
    public Activity getById(int id) throws SQLException {
        // Préparation de la requête
        String query = "SELECT * FROM Activity WHERE activityId = ?";
        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, id);

        // Exécution de la requête
        ResultSet resultSet = pre.executeQuery();

        // Vérification si une activité est trouvée
        if (resultSet.next()) {
            // Extraction des données
            int activityId = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            int capacity = resultSet.getInt(4);
            Date date = resultSet.getDate(5);
            Time hour = resultSet.getTime(6);
            int duration = resultSet.getInt(7);

            // Création et retour de l'objet Activity
            return new Activity(activityId, name, description, capacity, date, hour, duration);
        }

        // Si aucune activité n'est trouvée
        return null;
    }

}
