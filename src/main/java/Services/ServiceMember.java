package Services;

import Entite.Activity;
import Entite.Member;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceMember implements IService<Member> {
    private Connection conn = DataSource.getInstance().getCon();
    private Statement stat= null ;
    public ServiceMember() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouter(Member member) throws SQLException {

    }

    @Override
    public void supprimer(Member member) throws SQLException {

    }

    @Override
    public void update(Member member) throws SQLException {

    }

    @Override
    public List<Member> getAll() throws SQLException {
        List<Member> list = new ArrayList<>();

        ResultSet reset = stat.executeQuery("SELECT * FROM Member");
        while (reset.next()) {
            // Create a Member object and populate its fields
            Member member = new Member();
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

            // Add the Member object to the list
            list.add(member);
        }
        return list;
    }
    @Override
    public Member getById(int id) throws SQLException {
        return null;
    }
}
