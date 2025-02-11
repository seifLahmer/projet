package Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Authentification {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Connection conn;

    public Authentification(Connection conn) {
        this.conn = conn;
    }


    private String generateToken(String Email) {
        return Jwts.builder()
                .setSubject(Email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expire après 1h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Inscription
    public boolean signup(String Email, String Password) throws SQLException {

        String checkQuery = "SELECT COUNT(*) FROM Member WHERE Email = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, Email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // L'utilisateur existe déjà
            }
        }


        String hashedPassword = BCrypt.hashpw(Password, BCrypt.gensalt());


        String insertQuery = "INSERT INTO Member (Email, Password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, Email);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            return true;
        }
    }

    public static void saveToken(String token) {
        System.setProperty("jwtToken", token);
    }


    public String login(String Email, String Password) throws SQLException {

            String query = "SELECT Password FROM Member WHERE Email = ?"; // Adjust based on your table name
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, Email);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


                    System.out.println("Stored Hash: " + storedHash);

                    if (Password.equals(storedHash)) {
                        return generateToken(Email);
                    }
                    else {
                        System.out.println("Wrong Password");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}
