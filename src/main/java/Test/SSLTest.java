
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class SSLTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://smtp.gmail.com");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println("Connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}