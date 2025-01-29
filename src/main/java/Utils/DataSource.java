package Utils;

import java.sql.*;

public class DataSource {


    private static DataSource data;
    private String url = "jdbc:mysql://localhost:3306/projet_gym";
    private String user = "root";
    private String password = "";
    private Connection conn;


    private DataSource() {
        try {
            conn=DriverManager.getConnection(url,user,password);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getCon() {
        return conn;
    }

    public static DataSource getInstance() {
        if (data == null) data = new DataSource();
        return data;
    }
}
