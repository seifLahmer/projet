package Services;

import Entite.Member;
import Utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.* ;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Utils.DataSource.data;

public class ServiceMember implements IService<Member> {
    private Connection conn = DataSource.getInstance().getCon();

    private Statement stat= null ;

    public ServiceMember() {
        try {
            stat = conn.createStatement();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Ajouter(Member m) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO Member VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pst.setInt(1, m.getMemberId());
        pst.setString(2, m.getFirstName());
        pst.setString(3, m.getLastName());
        pst.setString(4, m.getEmail());
        pst.setString(5,m.getPassword());
        pst.setString(6, String.valueOf(m.getGender()));
        pst.setString(7, m.getPhoneNumber());
        pst.setString(8, m.getSchedule());
        pst.setDate(9, m.getStartDate());
        pst.setDate(10, m.getEndDate());
        pst.setFloat(11, m.getPrice());
        pst.setString(12, m.getStatus() ? "1" : "2");
        pst.setString(13, m.getSubscriptionType());
        pst.setString(14, m.getRole());

        pst.executeUpdate();
        System.out.println("Member ajouté avec succès !");
    }
    public void Supprimer(Member m) throws SQLException{
        PreparedStatement pst = conn.prepareStatement("delete from Member where MemberId=?");
        pst.setInt(1,m.getMemberId());
        pst.executeUpdate();
        System.out.println("Member deleted");
    }



    public void Update(Member m, Map<String, Object> data) throws SQLException {
        String query = "UPDATE Member SET ";
        query += String.join(" = ?, ", data.keySet()) + " = ? WHERE MemberId = ?";

        PreparedStatement pst = conn.prepareStatement(query);
        int index = 1;

        for (Object value : data.values()) {
            pst.setObject(index++, value);
        }

        pst.setInt(index, m.getMemberId()); // Récupération correcte de MemberId
        pst.executeUpdate();

        System.out.println("Member updated");
    }

    public List<Member> findAll() throws SQLException {
        List<Member> members = new ArrayList<Member>();
        ResultSet rs = stat.executeQuery("select * from Member");

        while (rs.next()) {
            int memberId = rs.getInt("MemberId");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            String email = rs.getString("Email");
            String Password = rs.getString("Password");
            char gender = rs.getString("Gender").charAt(0);
            String phoneNumber = rs.getString("PhoneNumber");
            String schedule = rs.getString("Schedule");
            Date startDate = rs.getDate("StartDate");
            Date endDate = rs.getDate("EndDate");
            float price = rs.getFloat("Price");
            boolean status = rs.getString("Status").equals("A");
            String subscriptionType = rs.getString("SubscriptionType");
            String role = rs.getString("Role");
            Member m = new Member(memberId,firstName,lastName,email,Password,gender,phoneNumber,schedule,startDate,endDate,price,status,subscriptionType,role);
            members.add(m);

        }
        return members;

    }

    public Member findById(int id) throws SQLException{
        String query = "SELECT * from Member where MemberId=?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setInt(1,id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            while (rs.next()) {
                int memberId = rs.getInt("MemberId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String Password = rs.getString("Password");
                char gender = rs.getString("Gender").charAt(0);
                String phoneNumber = rs.getString("PhoneNumber");
                String schedule = rs.getString("Schedule");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                float price = rs.getFloat("Price");
                boolean status = rs.getString("Status").equals("A");
                String subscriptionType = rs.getString("SubscriptionType");
                String role = rs.getString("Role");
                return new Member(memberId,firstName,lastName,email,Password,gender,phoneNumber,schedule,startDate,endDate,price,status,subscriptionType,role);
            }
        }

    return null ;

    }






}
