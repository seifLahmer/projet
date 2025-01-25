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
        PreparedStatement pre= conn.prepareStatement("INSERT INTO Activity (activityname,description,maxMembers, date,hour,duration,memberId) VALUES (?,?,?,?,?,? ,?);");
        pre.setInt(3,activity.getMaxMembers());
        pre.setString(2,activity.getDescription());
        pre.setString(1, activity.getActivityName());
        pre.setDate(4, new java.sql.Date(activity.getDate().getTime()));
        pre.setTime(5,activity.getHour());
        pre.setInt(6,activity.getDuration());
        pre.setInt(7,activity.getMemberId());

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
    public void update(Activity activity) throws SQLException {
        // Construction de la requête SQL
        String query = "UPDATE Activity SET activityName = ?, description = ?, memberId = ?, date = ?, duration = ?, maxMembers = ?, hour = ? WHERE activityId = ?";

        // Préparation de la requête
        PreparedStatement pre = conn.prepareStatement(query);

        // Ajout des valeurs dans le PreparedStatement
        pre.setString(1, activity.getActivityName());
        pre.setString(2, activity.getDescription());
        pre.setInt(3, activity.getMemberId());
        pre.setDate(4, new java.sql.Date(activity.getDate().getTime()));
        pre.setInt(5, activity.getDuration());
        pre.setInt(6, activity.getMaxMembers());
        pre.setTime(7, activity.getHour());

        // Ajout de l'ID de l'activité (clé primaire)
        pre.setInt(8, activity.getActivityId());

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
            int memberId = reset.getInt(2);
            String name=reset.getString(3);
            String description  =reset.getString(4);
            int capacite = reset.getInt(8);
            Date date=reset.getDate(5);
            Time hour = reset.getTime(7);
            int duration = reset.getInt(6);

            Activity r=new Activity( activityId,  name,  description,  capacite,  date,  hour,  duration,  memberId);

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
        ResultSet reset = pre.executeQuery();

        // Vérification si une activité est trouvée
        if (reset.next()) {
            // Extraction des données
            int activityId=reset.getInt(1);
            int memberId = reset.getInt(2);
            String name=reset.getString(3);
            String description  =reset.getString(4);
            int capacite = reset.getInt(8);
            Date date=reset.getDate(5);
            Time hour = reset.getTime(7);
            int duration = reset.getInt(6);

            Activity r=new Activity( activityId,  name,  description,  capacite,  date,  hour,  duration,  memberId);
            return r;
        }

        // Si aucune activité n'est trouvée
        return null;
    }

}
