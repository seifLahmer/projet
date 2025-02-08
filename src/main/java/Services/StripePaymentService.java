package Services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.HashMap;
import java.util.Map;

public class StripePaymentService {

    private static final String STRIPE_SECRET_KEY = "sk_test_51Qq9lyIbDMIkeP5WbRRPZJH22dQqkEyeLilsilb0lpgBuFYZmK9ccRaCWQH2VsITC517r2DLeejuIw28Scf2z7Bd00hCj7RDin"; // Remplace avec ta clé API secrète

    public StripePaymentService() {
        Stripe.apiKey = STRIPE_SECRET_KEY;
    }

    public PaymentIntent createPayment(double amount) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (amount * 100)); // Stripe attend un montant en cents
        params.put("currency", "eur"); // Devise (peut être "usd", "eur", etc.)
        params.put("payment_method_types", new String[]{"card"});

        return PaymentIntent.create(params);
    }
}

