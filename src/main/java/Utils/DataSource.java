package Utils;
import java.sql.*;
public class DataSource {
    public static DataSource data;
    private String url = "jdbc:mysql://localhost:3306/projet_gym";
    private String user = "root";
    private String password = "";

    private Connection con;

    private DataSource() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public Connection getCon() {
        return con;
    }

    public static DataSource getInstance() {
        if (data == null) {
            data = new DataSource();
        }
        return data;


    }

}
