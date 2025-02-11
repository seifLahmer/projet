package Services;

import Entite.Members;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMembers implements IService<Members> {
    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat= null ;
    public ServiceMembers() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Members member) throws SQLException {

    }

    @Override
    public void supprimer(Members member) throws SQLException {

    }

    @Override
    public void update(Members member) throws SQLException {

    }

    @Override
    public List<Members> getAll() throws SQLException {
        List<Members> list = new ArrayList<>();

        ResultSet reset = stat.executeQuery("SELECT * FROM Member");
        while (reset.next()) {
            // Create a Members object and populate its fields
            Members member = new Members();
            member.setMemberId(reset.getInt("MemberId"));
            member.setFirstName(reset.getString("FirstName"));
            member.setLastName(reset.getString("LastName"));
            member.setEmail(reset.getString("Email"));
            member.setGender(reset.getString("Gender").charAt(0)); // Assuming gender is a single character
            member.setPhoneNumber(reset.getString("PhoneNumber"));
            member.setSchedule(reset.getString("Schedule"));
            member.setStartDate(reset.getDate("StartDate"));
            member.setEndDate(reset.getDate("EndDate"));
            member.setPrice(reset.getFloat("Price"));
            member.setStatus(reset.getBoolean("Status")); // Assuming 'Status' is a boolean
            member.setSubscriptionType(reset.getString("SubscriptionType"));
            member.setRole(reset.getString("role"));

            // Add the Members object to the list
            list.add(member);
        }
        return list;
    }
    @Override
    public Members getById(int id) throws SQLException {
        return null;
    }
}
