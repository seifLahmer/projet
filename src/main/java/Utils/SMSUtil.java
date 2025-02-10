package Utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Alert;

public class SMSUtil {
    // Twilio Account Credentials
    private static final String ACCOUNT_SID = "AC32684f6f2eb463f84ab9662797eceef7";
    private static final String AUTH_TOKEN = "4d1cfae9c582a549e22e157ff1fcac88";
    private static final String TWILIO_PHONE_NUMBER = "+16402463489";

    static {
        try {
            System.out.println("Initializing Twilio with Account SID: " + ACCOUNT_SID);
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            System.out.println("Twilio initialization successful");
        } catch (Exception e) {
            System.err.println("Failed to initialize Twilio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean sendPaymentConfirmation(String phoneNumber, double amount, String memberId) {
        try {
            // Debug logging
            System.out.println("\nSending SMS:");
            System.out.println("To: " + phoneNumber);
            System.out.println("From: " + TWILIO_PHONE_NUMBER);
            System.out.println("Account SID: " + ACCOUNT_SID);

            String messageBody = String.format(
                "GYM Payment Confirmation\n" +
                "Amount: %.2f TND\n" +
                "Member ID: %s\n" +
                "Date/Time: %s\n" +
                "Thank you for your payment!",
                amount,
                memberId,
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            );

            System.out.println("Message body: " + messageBody);

            Message message = Message.creator(
                new PhoneNumber("+21629558258"),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                messageBody
            ).create();

            System.out.println("Message SID: " + message.getSid());
            System.out.println("Message Status: " + message.getStatus());

            return true;

        } catch (com.twilio.exception.ApiException e) {
            System.err.println("Twilio API Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getCode());
            System.err.println("More Info: " + e.getMoreInfo());
            showAlert(Alert.AlertType.ERROR, "SMS Error", 
                     "Failed to send SMS. Error code: " + e.getCode() + "\n" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "SMS Error", 
                     "Failed to send SMS: " + e.getMessage());
            return false;
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 